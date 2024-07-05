package com.spring.bookstore.controller;

import com.spring.bookstore.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/cart")
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @GetMapping("add/{id}")
    public ResponseEntity<?> addToCart(@PathVariable("id") int bookId) {
        this.cartService.addToCart(bookId);
        return ResponseEntity.ok("Successfully!");
    }

    @GetMapping("clear")
    public ResponseEntity<String> clearCart() {
        this.cartService.removeCart();
        return ResponseEntity.ok("Successfully!");
    }

    @GetMapping("remove/{id}")
    public ResponseEntity<String> removeItem(@PathVariable("id") int bookId) {
        this.cartService.removeItem(bookId);
        return ResponseEntity.ok("Successfully!");
    }

    @GetMapping("minus/{id}")
    public ResponseEntity<String> minusItem(@PathVariable("id") int bookId) {
        this.cartService.minusItem(bookId);
        return ResponseEntity.ok("Successfully!");
    }

    @GetMapping("items")
    public ResponseEntity<Map<Integer, Integer>> getItems() {
        Map<Integer, Integer> items = this.cartService.getItems();
        System.out.println(items);
        return ResponseEntity.ok(items);
    }

    @GetMapping("total-items")
    public ResponseEntity<Integer> getTotalItems() {
        return ResponseEntity.ok(this.cartService.getTotalItems());
    }

    @GetMapping("total-quantities")
    public ResponseEntity<Integer> getTotalQuantities() {
        return ResponseEntity.ok(this.cartService.getTotalQuantities());
    }

    @GetMapping("total-amount")
    public ResponseEntity<Double> getTotalAmount() {
        return ResponseEntity.ok(this.cartService.getTotalAmount());
    }
}
