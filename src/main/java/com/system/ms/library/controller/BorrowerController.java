package com.system.ms.library.controller;

import com.system.ms.library.model.BookDTO;
import com.system.ms.library.model.BorrowerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/borrower")
public class BorrowerController {

  @PostMapping("/register-borrower")
  public ResponseEntity<BorrowerDTO> registerBorrower() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/deregister-borrower")
  public ResponseEntity<BorrowerDTO> deregisterBorrower() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/borrow-book/{isbn}")
  public ResponseEntity<BookDTO> borrowBook() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/return-book/{isbn}")
  public ResponseEntity<BookDTO> returnBook() {
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
