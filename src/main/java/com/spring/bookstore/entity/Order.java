package com.spring.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "customer", referencedColumnName = "user_id")
    private Customer customer;

    @CreatedDate
    private LocalDate orderDate;

    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false, length = 50)
    private String recipientName;

    @Column(nullable = false, length = 10)
    private String recipientPhone;

    @Column(nullable = false, unique = true)
    private String paymentMethod;

    @Column(nullable = false, precision = 12)
    private float shippingCost;

    @Column(nullable = false)
    private int bookCopies;

    @Column(nullable = false, precision = 12)
    private float total;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails;
}
