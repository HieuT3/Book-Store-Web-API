package com.spring.bookstore.controller;

import com.spring.bookstore.entity.Book;
import com.spring.bookstore.entity.Category;
import com.spring.bookstore.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping("{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int categoryId) {
        try {
            Category category = this.categoryService.getCategory(categoryId);
            return new ResponseEntity<>(category, HttpStatus.FOUND);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = this.categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            Category savedCategory = this.categoryService.createCategory(category);
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}/update")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int categoryId,
                                            @RequestBody Category category) {
        try {
            Category updatedCategory = this.categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int categoryId) {
        try {
            this.categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok("The category has been successfully deleted!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}/books")
    public ResponseEntity<?> getBooksByCategory(@PathVariable("id") int categoryId) {
        try {
            List<Book> books = this.categoryService.getBooksByCategory(categoryId);
            return ResponseEntity.ok(books);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
