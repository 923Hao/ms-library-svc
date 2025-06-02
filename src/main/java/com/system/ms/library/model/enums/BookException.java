package com.system.ms.library.model.enums;

import lombok.Getter;

public enum BookException {
  BOOK_ISBN_NOT_MATCH("10001", "Entered title and author is not matched with existing ISBN"),
  BOOK_NOT_FOUND("10002", "Book not found"),
  NO_BOOKS_AVAILABLE("10003", "No books available to borrow or deregister for this ISBN")
  ;

  private final String code;
  @Getter
  private final String message;

  BookException(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return "BOOK-" + code;
  }
}
