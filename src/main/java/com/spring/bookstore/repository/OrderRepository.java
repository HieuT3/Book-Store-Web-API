package com.spring.bookstore.repository;

import com.spring.bookstore.entity.Customer;
import com.spring.bookstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByCustomer(Customer customer);
}
