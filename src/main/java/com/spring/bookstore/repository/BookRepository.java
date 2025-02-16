package com.spring.bookstore.repository;

import com.spring.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("select b from Book b")
    List<Book> findAllBooksOnly();

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByAuthorsName(String name, Pageable pageable);

    Page<Book> findByCategoriesName(String name, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseAndAuthorsName(String title, String name, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseAndCategoriesName(String title, String name, Pageable pageable);

    Page<Book> findByAuthorsNameAndCategoriesName(String authorName, String categoryName, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseAndAuthorsNameAndCategoriesName(String title, String categoryName, String authorName, Pageable pageable);

    boolean existsByTitle(String title);

    Optional<Book> findByTitle(String title);

}
