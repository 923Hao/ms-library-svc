package com.system.ms.library.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BorrowerException extends RuntimeException {

  private String code;
  private String message;

  public BorrowerException(com.system.ms.library.model.enums.BorrowerException bookException) {
    this.code = bookException.getCode();
    this.message = bookException.getMessage();
  }
}
