package com.system.ms.library.repository.entity;

import com.system.ms.library.model.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
}
