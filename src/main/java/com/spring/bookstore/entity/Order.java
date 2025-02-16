package com.spring.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    @CreationTimestamp
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false, length = 50)
    private String recipientName;

    @Column(nullable = false, length = 10)
    private String recipientPhone;

    @Column(nullable = false, unique = true)
    private String paymentMethod;

    @Column(nullable = false, precision = 12)
    private double shippingCost;

    @Column(nullable = false)
    private int bookCopies;

    @Column(nullable = false, precision = 12)
    private double total;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails = new HashSet<>();
}
