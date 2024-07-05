package com.spring.bookstore.repository;

import com.spring.bookstore.entity.Book;
import com.spring.bookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByTitle(String title);

    Optional<Book> findByTitle(String title);

    List<Book> findAllByCategory(Category category);
}
