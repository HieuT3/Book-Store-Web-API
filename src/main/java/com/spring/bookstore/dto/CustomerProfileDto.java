package com.spring.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerProfileDto {
    private int userId;
    private String email;
    private String fullName;
    private String phone;
    private String address;
}
