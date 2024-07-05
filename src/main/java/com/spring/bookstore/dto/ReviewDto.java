package com.spring.bookstore.dto;

import com.spring.bookstore.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {
    private int reviewId;
    private Book book;
    private String customerFullName;
    private String comment;
}
