package com.spring.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    @ManyToOne
    @JoinColumn(name = "book", referencedColumnName = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "customer", referencedColumnName = "user_id")
    private Customer customer;

    @Column(nullable = false, length = 16777215)
    private String comment;

    @CreationTimestamp
    private LocalDateTime reviewTime;
}
