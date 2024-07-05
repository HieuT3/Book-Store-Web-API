package com.spring.bookstore;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedHashMap;
import java.util.Map;

public class BCryptTest {

    public static void main(String[] args) {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        System.out.println(passwordEncoder.encode("123456"));
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        map.put(1, 1);
        map.put(2, 3);
        map.put(3, 2);
        map.put(4, 5);
        System.out.println(map.keySet());
    }
}
