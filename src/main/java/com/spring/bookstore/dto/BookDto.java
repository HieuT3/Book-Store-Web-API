package com.spring.bookstore.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookDto {
    private int bookId;
    private String title;
    private String description;
    private String isbn;
    private MultipartFile multipartFile;
    private float price;
    private String publishDate;
}
