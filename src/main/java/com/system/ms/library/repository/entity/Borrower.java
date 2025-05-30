package com.system.ms.library.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "t_borrower")
public class Borrower {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "email_address")
  private String emailAddress;
}
