package com.spring.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private int orderDetailId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "orders", referencedColumnName = "order_id")
    private Order orders;

    @ManyToOne
    @JoinColumn(name = "book", referencedColumnName = "book_id")
    private Book book;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 12)
    private double subTotal;

}
