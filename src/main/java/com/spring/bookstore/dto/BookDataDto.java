package com.spring.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDataDto {
    private int bookId;
    private String title;
    private String description;
    private String isbn;
    private String imageURL;
    private double price;
    private LocalDate publishDate;
}
