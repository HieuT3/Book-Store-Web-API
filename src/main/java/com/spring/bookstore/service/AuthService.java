package com.spring.bookstore.service;

import com.spring.bookstore.dto.LoginUserDto;
import com.spring.bookstore.entity.Users;
import com.spring.bookstore.repository.UserRepository;
import com.spring.bookstore.security.JwtAuthenticationProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    public Users authenticate(LoginUserDto loginUserDto) {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }

        return this.userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(
                        () -> new EntityNotFoundException("User with email " + loginUserDto.getEmail() + " not found!")
                );
    }
}
