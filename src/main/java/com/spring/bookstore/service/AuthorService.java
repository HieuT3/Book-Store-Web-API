package com.spring.bookstore.service;

import com.spring.bookstore.entity.Author;
import com.spring.bookstore.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {
    private AuthorRepository authorRepository;

    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }
}
