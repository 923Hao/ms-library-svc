package com.system.ms.library.repository;

import com.system.ms.library.model.enums.Status;
import com.system.ms.library.repository.entity.Borrower;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BorrowerRepository extends JpaRepository<Borrower, UUID> {

  Optional<Borrower> findByEmailAddressAndStatus(String emailAddress, Status status);

  Optional<Borrower> findByNameAndEmailAddressAndStatus(String name, String emailAddress,
      Status status);
}
