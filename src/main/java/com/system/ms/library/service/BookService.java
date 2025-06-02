package com.system.ms.library.service;

import static com.system.ms.library.model.enums.BookException.BOOK_ISBN_NOT_MATCH;
import static com.system.ms.library.model.enums.BookException.BOOK_NOT_FOUND;
import static com.system.ms.library.model.enums.BookException.NO_BOOKS_AVAILABLE;
import static com.system.ms.library.model.enums.BorrowerException.BORROWER_NOT_BORROWING_THIS_BOOK;
import static com.system.ms.library.model.enums.BorrowerException.BORROWER_PENDING_RETURN_BOOK;

import com.system.ms.library.model.BookDTO;
import com.system.ms.library.model.enums.Status;
import com.system.ms.library.model.exception.BookException;
import com.system.ms.library.model.exception.BorrowerException;
import com.system.ms.library.repository.BookRepository;
import com.system.ms.library.repository.entity.Book;
import java.util.Objects;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private ModelMapper modelMapper;

  public Page<BookDTO> getBookList(String title, String author, Pageable pageable) {
    return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndStatus(
        title, author, Status.A, pageable).map(book -> modelMapper.map(book, BookDTO.class));
  }

  public BookDTO registerBook(BookDTO bookDTO) {
    var bookList = bookRepository.findAllByIsbnAndStatus(bookDTO.getIsbn(), Status.A);

    bookList.stream().findFirst().ifPresent(book -> {
      if (!book.getTitle().equals(bookDTO.getTitle()) || !book.getAuthor()
          .equals(bookDTO.getAuthor())) {
        throw new BookException(BOOK_ISBN_NOT_MATCH);
      }
    });

    var book = modelMapper.map(bookDTO, Book.class);
    book.setStatus(Status.A);
    var savedBook = bookRepository.saveAndFlush(book);

    return modelMapper.map(savedBook, BookDTO.class);
  }

  public BookDTO deregisterBook(BookDTO bookDTO) {
    var book = validateBookExistenceAndBorrowStatus(bookDTO.getIsbn(), null);
    book.setStatus(Status.I);
    var deletedBook = bookRepository.saveAndFlush(book);

    return modelMapper.map(deletedBook, BookDTO.class);
  }

  public void validateBorrowingStatus(UUID borrowerUuid) {
    var bookList = bookRepository.findAllByBorrowedBy(borrowerUuid);

    if (!bookList.isEmpty()) {
      throw new BorrowerException(BORROWER_PENDING_RETURN_BOOK);
    }
  }

  public Book validateBookExistenceAndBorrowStatus(String isbn, UUID borrowerUuid) {
    var bookList = bookRepository.findAllByIsbnAndStatus(isbn, Status.A);

    if (bookList.isEmpty()) {
      throw new BookException(BOOK_NOT_FOUND);
    }

    return bookList.stream().filter(book -> Objects.equals(borrowerUuid, book.getBorrowedBy()))
        .findFirst().orElseThrow(() -> null == borrowerUuid ? new BookException(NO_BOOKS_AVAILABLE)
            : new BorrowerException(BORROWER_NOT_BORROWING_THIS_BOOK));
  }

  public void borrowBook(Book book, UUID borrowerUuid) {
    book.setBorrowedBy(borrowerUuid);
    bookRepository.saveAndFlush(book);
  }

  public void returnBook(Book book) {
    book.setBorrowedBy(null);
    bookRepository.saveAndFlush(book);
  }
}
