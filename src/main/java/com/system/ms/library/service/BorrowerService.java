package com.system.ms.library.service;

import static com.system.ms.library.model.enums.BorrowerException.BORROWER_EXIST;
import static com.system.ms.library.model.enums.BorrowerException.BORROWER_NOT_EXIST;

import com.system.ms.library.model.BookDTO;
import com.system.ms.library.model.BorrowerDTO;
import com.system.ms.library.model.enums.Status;
import com.system.ms.library.model.exception.BorrowerException;
import com.system.ms.library.repository.BorrowerRepository;
import com.system.ms.library.repository.entity.Borrower;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowerService {

  @Autowired
  private BorrowerRepository borrowerRepository;

  @Autowired
  private BookService bookService;

  @Autowired
  private ModelMapper modelMapper;

  public BorrowerDTO registerBorrower(BorrowerDTO borrowerDTO) {
    var borrowerOptional = borrowerRepository.findByEmailAddressAndStatus(
        borrowerDTO.getEmailAddress(), Status.A);

    if (borrowerOptional.isPresent()) {
      throw new BorrowerException(BORROWER_EXIST);
    }

    var borrower = modelMapper.map(borrowerDTO, Borrower.class);
    borrower.setStatus(Status.A);
    var savedBorrower = borrowerRepository.saveAndFlush(borrower);

    return modelMapper.map(savedBorrower, BorrowerDTO.class);
  }

  public BorrowerDTO deregisterBorrower(BorrowerDTO borrowerDTO) {
    var borrower = validateIfBorrowerExist(borrowerDTO);
    bookService.validateBorrowingStatus(borrower.getId());
    borrower.setStatus(Status.I);
    var deletedBorrower = borrowerRepository.saveAndFlush(borrower);

    return modelMapper.map(deletedBorrower, BorrowerDTO.class);
  }

  public BookDTO borrowBook(String isbn, BorrowerDTO borrowerDTO) {
    var borrower = validateIfBorrowerExist(borrowerDTO);
    var book = bookService.validateBookExistenceAndBorrowStatus(isbn, null);
    bookService.borrowBook(book, borrower.getId());

    return modelMapper.map(book, BookDTO.class);
  }

  public BookDTO returnBook(String isbn, BorrowerDTO borrowerDTO) {
    var borrower = validateIfBorrowerExist(borrowerDTO);
    var book = bookService.validateBookExistenceAndBorrowStatus(isbn, borrower.getId());
    bookService.returnBook(book);

    return modelMapper.map(book, BookDTO.class);
  }

  private Borrower validateIfBorrowerExist(BorrowerDTO borrowerDTO) {
    var borrowerOptional = borrowerRepository.findByNameAndEmailAddressAndStatus(
        borrowerDTO.getName(), borrowerDTO.getEmailAddress(), Status.A);

    if (borrowerOptional.isEmpty()) {
      throw new BorrowerException(BORROWER_NOT_EXIST);
    }

    return borrowerOptional.get();
  }
}
