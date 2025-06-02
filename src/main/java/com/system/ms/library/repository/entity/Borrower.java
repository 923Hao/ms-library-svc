package com.system.ms.library.repository.entity;

import com.system.ms.library.model.enums.Status;
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

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
}
