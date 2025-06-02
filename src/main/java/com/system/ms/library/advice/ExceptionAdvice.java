package com.system.ms.library.advice;

import com.system.ms.library.model.exception.BookException;
import com.system.ms.library.model.exception.BorrowerException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.ok().body(errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
    Map<String, String> errors = ex.getConstraintViolations()
        .stream()
        .collect(Collectors.toMap(
            violation -> {
              String path = violation.getPropertyPath().toString();
              if (path.contains(".")) {
                path = path.substring(path.lastIndexOf('.') + 1);
              }
              return path;
            },
            ConstraintViolation::getMessage
        ));

    return ResponseEntity.ok().body(errors);
  }

  @ExceptionHandler(BookException.class)
  public ResponseEntity<Map<String, String>> handleBookException(BookException ex) {
    return ResponseEntity.ok()
        .body(Map.of("errorCode", ex.getCode(), "errorMessage", ex.getMessage()));
  }

  @ExceptionHandler(BorrowerException.class)
  public ResponseEntity<Map<String, String>> handleBorrowerException(BorrowerException ex) {
    return ResponseEntity.ok()
        .body(Map.of("errorCode", ex.getCode(), "errorMessage", ex.getMessage()));
  }
}
