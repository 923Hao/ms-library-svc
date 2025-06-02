package com.system.ms.library.model.enums;

import lombok.Getter;

public enum BorrowerException {
  BORROWER_PENDING_RETURN_BOOK("20001", "Borrower still have pending return book"),
  BORROWER_NOT_BORROWING_THIS_BOOK("20002", "Borrower does not borrowing this book"),
  BORROWER_EXIST("20003", "Borrower with this email address already exist"),
  BORROWER_NOT_EXIST("20004", "Borrower does not exist")
  ;

  private final String code;
  @Getter
  private final String message;

  BorrowerException(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return "BORROWER-" + code;
  }
}
