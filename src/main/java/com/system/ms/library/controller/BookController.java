package com.system.ms.library.controller;

import com.system.ms.library.model.BookDTO;
import com.system.ms.library.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/book")
@Validated
public class BookController {

  @Autowired
  private BookService bookService;

  @GetMapping("/book-list")
  public ResponseEntity<Page<BookDTO>> getBookList(
      @RequestParam(required = false, name = "title") @Pattern(regexp = "^[a-zA-Z0-9 :,.\\-?]+$", message = "Only alphanumeric characters and symbols (: , . - ?) are allowed") @Size(max = 100, message = "Title characters count could not be more than 100") String title,
      @RequestParam(required = false, name = "author") @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Only alphanumeric characters are allowed") @Size(max = 100, message = "Author characters count could not be more than 100") String author,
      @PageableDefault(sort = "title") Pageable pageable) {
    return ResponseEntity.ok().body(bookService.getBookList(title, author, pageable));
  }

  @PostMapping("/register-book")
  public ResponseEntity<BookDTO> registerBook(@Valid @RequestBody BookDTO bookDTO) {
    return ResponseEntity.ok().body(bookService.registerBook(bookDTO));
  }

  @PostMapping("/deregister-book")
  public ResponseEntity<BookDTO> deregisterBook(@Valid @RequestBody BookDTO bookDTO) {
    return ResponseEntity.ok().body(bookService.deregisterBook(bookDTO));
  }
}
