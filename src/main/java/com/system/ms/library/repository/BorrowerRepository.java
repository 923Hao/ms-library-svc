package com.system.ms.library.repository;

import com.system.ms.library.repository.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BorrowerRepository extends JpaRepository<Borrower, UUID> {
}
