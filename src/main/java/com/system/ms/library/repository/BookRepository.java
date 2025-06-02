package com.system.ms.library.repository;

import com.system.ms.library.model.enums.Status;
import com.system.ms.library.repository.entity.Book;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, UUID> {

  @Query("""
          SELECT b FROM Book b
          WHERE (LOWER(b.title) LIKE LOWER(CONCAT('%', COALESCE(:title, ''), '%')))
          AND (LOWER(b.author) LIKE LOWER(CONCAT('%', COALESCE(:author, ''), '%')))
          AND b.status = :status
      """)
  Page<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndStatus(
      @Param("title") String title, @Param("author") String author, @Param("status") Status status,
      Pageable pageable);

  List<Book> findAllByIsbnAndStatus(String isbn, Status status);

  List<Book> findAllByBorrowedBy(UUID borrowedBy);
}
