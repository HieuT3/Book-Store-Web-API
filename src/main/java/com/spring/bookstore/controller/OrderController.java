package com.spring.bookstore.controller;

import com.spring.bookstore.dto.OrderRequestDto;
import com.spring.bookstore.entity.Order;
import com.spring.bookstore.entity.OrderStatusEnum;
import com.spring.bookstore.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @GetMapping("{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") int orderId) {
        try {
            return ResponseEntity.ok(this.orderService.getOrderById(orderId));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(this.orderService.getAllOrders());
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return new ResponseEntity<>(this.orderService.placeOrder(orderRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("customer")
    public ResponseEntity<List<Order>> getAllOrdersByCustomer() {
        return ResponseEntity.ok(this.orderService.getAllOrderByCustomer());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") int orderId) {
        try {
            this.orderService.deleteOrder(orderId);
            return ResponseEntity.ok("The order has been deleted successfully!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable("id") int orderId,
                                               @RequestParam("status") OrderStatusEnum status) {
        try {
            return ResponseEntity.ok(this.orderService.updateOrderStatus(orderId, status));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
