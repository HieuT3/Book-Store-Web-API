package com.spring.bookstore.mapper;

import com.spring.bookstore.dto.BookDto;
import com.spring.bookstore.entity.Book;
import com.spring.bookstore.entity.Category;
import com.spring.bookstore.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class BookMapper {

    private CategoryRepository categoryRepository;

    public Book convertToBook(BookDto bookDto, String imageURL) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Book book = new Book();
        Category category = this.categoryRepository.findById(bookDto.getCategory()).get();
        book.setCategory(category);
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setDescription(bookDto.getDescription());
        book.setIsbn(bookDto.getIsbn());
        book.setImageURL(imageURL);
        book.setPrice(bookDto.getPrice());
        book.setPublishDate(LocalDate.parse(bookDto.getPublishDate(), formatter));
        return book;
    }
}
