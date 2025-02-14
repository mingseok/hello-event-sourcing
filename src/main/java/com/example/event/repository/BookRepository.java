package com.example.event.repository;

import com.example.event.projection.BookProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookProjection, String> {
}
