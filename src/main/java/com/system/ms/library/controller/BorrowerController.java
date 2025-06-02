package com.system.ms.library.controller;

import com.system.ms.library.model.BookDTO;
import com.system.ms.library.model.BorrowerDTO;
import com.system.ms.library.service.BorrowerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/borrower")
@Validated
public class BorrowerController {

  @Autowired
  private BorrowerService borrowerService;

  @PostMapping("/register-borrower")
  public ResponseEntity<BorrowerDTO> registerBorrower(@RequestBody BorrowerDTO borrowerDTO) {
    return ResponseEntity.ok().body(borrowerService.registerBorrower(borrowerDTO));
  }

  @PostMapping("/deregister-borrower")
  public ResponseEntity<BorrowerDTO> deregisterBorrower(@RequestBody BorrowerDTO borrowerDTO) {
    return ResponseEntity.ok().body(borrowerService.deregisterBorrower(borrowerDTO));
  }

  @PostMapping("/borrow-book/{isbn}")
  public ResponseEntity<BookDTO> borrowBook(
      @PathVariable @Pattern(regexp = "^[A-Z]{3}[0-9]{5}$", message = "ISBN must start with 3 capital letters followed by 5 digits (e.g. ABC12345)") String isbn,
      @Valid @RequestBody BorrowerDTO borrowerDTO) {
    return ResponseEntity.ok().body(borrowerService.borrowBook(isbn, borrowerDTO));
  }

  @PostMapping("/return-book/{isbn}")
  public ResponseEntity<BookDTO> returnBook(
      @PathVariable @Pattern(regexp = "^[A-Z]{3}[0-9]{5}$", message = "ISBN must start with 3 capital letters followed by 5 digits (e.g. ABC12345)") String isbn,
      @Valid @RequestBody BorrowerDTO borrowerDTO) {
    return ResponseEntity.ok().body(borrowerService.returnBook(isbn, borrowerDTO));
  }
}
