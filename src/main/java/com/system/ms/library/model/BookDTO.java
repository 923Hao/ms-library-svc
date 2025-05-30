package com.system.ms.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

  private UUID id;
  private String isbn;
  private String title;
  private String author;
  private UUID borrowedBy;
}
