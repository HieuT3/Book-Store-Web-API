package com.spring.bookstore.repository;

import com.spring.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByTitle(String title);

    Optional<Book> findByTitle(String title);
}