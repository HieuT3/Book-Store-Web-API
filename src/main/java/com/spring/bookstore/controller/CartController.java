package com.spring.bookstore.controller;

import com.spring.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/redis")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("save")
    public ResponseEntity<?> save(@RequestParam("key") String key, @RequestParam("value") Object value) {
        cartService.save(key, value);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get/{key}")
    public ResponseEntity<?> get(@PathVariable("key") String key) {
        return ResponseEntity.ok(cartService.get(key));
    }

    @DeleteMapping("delete/{key}")
    public ResponseEntity<?> delete(@PathVariable("key") String key) {
         this.cartService.delete(key);
         return ResponseEntity.ok("Deleted successfully!");
    }
}
