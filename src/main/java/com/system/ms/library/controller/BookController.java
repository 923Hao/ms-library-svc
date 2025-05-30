package com.system.ms.library.controller;

import com.system.ms.library.model.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/book")
public class BookController {

  @GetMapping("/book-list")
  public ResponseEntity<Page<BookDTO>> getBookList() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/register-book")
  public ResponseEntity<BookDTO> registerBook() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/deregister-book")
  public ResponseEntity<BookDTO> deregisterBook() {
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
