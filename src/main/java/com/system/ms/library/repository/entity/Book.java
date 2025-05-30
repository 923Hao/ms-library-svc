package com.system.ms.library.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "t_book")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "isbn")
  private String isbn;

  @Column(name = "title")
  private String title;

  @Column(name = "author")
  private String author;

  @Column(name = "borrowed_by")
  private UUID borrowedBy;
}
