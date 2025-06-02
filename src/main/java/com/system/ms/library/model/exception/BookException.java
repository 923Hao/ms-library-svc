package com.system.ms.library.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookException extends RuntimeException {

  private String code;
  private String message;

  public BookException(com.system.ms.library.model.enums.BookException bookException) {
    this.code = bookException.getCode();
    this.message = bookException.getMessage();
  }
}
