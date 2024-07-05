package com.spring.bookstore.service;

import com.spring.bookstore.entity.Book;
import com.spring.bookstore.repository.BookRepository;
import com.spring.bookstore.utils.CartItem;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CartService {

    private BookRepository bookRepository;
    private UserService userService;
    private RedisTemplate<String, Object> redisTemplate;
    private final String CART_KEY_PREFIX = "cart:";

    private int getUserId() {
        return this.userService.getUserId();
    }

    public void addToCart(int bookId) {
        int userId = this.getUserId();

        String key = this.CART_KEY_PREFIX + "{" + userId + "}";
        CartItem cartItem = (CartItem) this.redisTemplate.opsForValue().get(key);
        if(cartItem == null) {
            cartItem = new CartItem();
        }

        cartItem.addToCart(bookId);
        this.redisTemplate.opsForValue().set(key, cartItem);
    }

    public void removeCart() {
        int userId = this.getUserId();

        String key = this.CART_KEY_PREFIX + "{" + userId + "}";
        CartItem cartItem = (CartItem) this.redisTemplate.opsForValue().get(key);

        if(cartItem == null) return;

        this.redisTemplate.delete(key);
    }

    public void removeItem(int bookId) {
        int userId = this.getUserId();

        String key = this.CART_KEY_PREFIX + "{" + userId + "}";
        CartItem cartItem = (CartItem) this.redisTemplate.opsForValue().get(key);

        if (cartItem == null) return;

        cartItem.removeItem(bookId);
        this.redisTemplate.opsForValue().set(key, cartItem);
    }

    public void minusItem(int bookId) {
        int userId = this.getUserId();

        String key = this.CART_KEY_PREFIX + "{" + userId + "}";
        CartItem cartItem = (CartItem) this.redisTemplate.opsForValue().get(key);

        if (cartItem == null) return;
        cartItem.minusItem(bookId);

        this.redisTemplate.opsForValue().set(key, cartItem);
    }

    public int getTotalItems() {
        int userId = this.getUserId();

        String key = this.CART_KEY_PREFIX + "{" + userId + "}";
        CartItem cartItem = (CartItem) this.redisTemplate.opsForValue().get(key);

        if (cartItem == null) return 0;
        return cartItem.totalItems();
    }

    public int getTotalQuantities() {
        int userId = this.getUserId();

        String key = this.CART_KEY_PREFIX + "{" + userId + "}";
        CartItem cartItem = (CartItem) this.redisTemplate.opsForValue().get(key);

        if (cartItem == null) return 0;
        return cartItem.totalQuantities();
    }

    public double getTotalAmount() {
        int userId = this.getUserId();

        String key = this.CART_KEY_PREFIX + "{" + userId + "}";
        CartItem cartItem = (CartItem) this.redisTemplate.opsForValue().get(key);

        if (cartItem == null) return 0;
        Map<Integer, Integer> items = cartItem.getCart();
        List<Book> books = this.bookRepository.findAllById(items.keySet());
        return books.stream().mapToDouble(book -> items.get(book.getBookId()) * book.getPrice()).sum();
    }

    public Map<Integer, Integer> getItems() {
        int userId = this.getUserId();

        String key = this.CART_KEY_PREFIX + "{" + userId + "}";
        CartItem cartItem = (CartItem) this.redisTemplate.opsForValue().get(key);

        if (cartItem == null) return null;
        return cartItem.getCart();
    }
}
