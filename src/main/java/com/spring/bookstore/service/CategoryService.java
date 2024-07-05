package com.spring.bookstore.service;

import com.spring.bookstore.entity.Book;
import com.spring.bookstore.entity.Category;
import com.spring.bookstore.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public Category getCategory(int categoryId) {
        return this.categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new EntityNotFoundException("The category with id " + categoryId + " not found!")
                );
    }

    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        boolean check = this.categoryRepository.existsByName(category.getName());
        if(check) {
            throw new IllegalArgumentException("The category already exists!");
        }
        return this.categoryRepository.save(category);
    }

    public Category updateCategory(int categoryId, Category category) {
        Category categoryById = this.categoryRepository.findById(categoryId).get();
        Category categoryByName = this.categoryRepository.findByName(category.getName()).orElse(null);

        if(categoryByName != null && categoryById.getCategoryId() != categoryByName.getCategoryId()) {
            throw new DataIntegrityViolationException("The category already exists!");
        }

        category.setCategoryId(categoryId);
        return this.categoryRepository.save(category);
    }

    public void deleteCategory(int categoryId) {
        this.categoryRepository.findById(categoryId).orElseThrow(
                () -> new EntityNotFoundException("The category with id " + categoryId + " not found!")
        );
        this.categoryRepository.deleteById(categoryId);
    }
}
