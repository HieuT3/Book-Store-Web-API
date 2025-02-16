package com.spring.bookstore.mapper;

import com.spring.bookstore.dto.BookDto;
import com.spring.bookstore.entity.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class BookMapper {

    public Book convertToBook(BookDto bookDto, String imageURL) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setIsbn(bookDto.getIsbn());
        book.setImageURL(imageURL);
        book.setPrice(bookDto.getPrice());
        book.setPublishDate(LocalDate.parse(bookDto.getPublishDate(), formatter));
        return book;
    }
}
