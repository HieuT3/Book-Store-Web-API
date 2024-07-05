package com.spring.bookstore.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class CartItem {

    private final Map<Integer, Integer> cart = new LinkedHashMap<>();

    public void addToCart(int bookId) {
        this.cart.put(bookId, this.cart.getOrDefault(bookId, 0) + 1);
    }

    public void clearCart() {
        this.cart.clear();
    }

    public void removeItem(int bookId) {
        this.cart.remove(bookId);
    }

    public void minusItem(int bookId) {
        if(this.cart.get(bookId) > 1) this.cart.put(bookId, this.cart.get(bookId) - 1);
        else this.cart.remove(bookId);
    }

    public int totalItems() {
        return this.cart.size();
    }

    public int totalQuantities() {
        return this.cart.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Map<Integer, Integer> getCart() {
        return this.cart;
    }

}
