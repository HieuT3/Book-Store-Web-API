package com.spring.bookstore.controller;

import com.spring.bookstore.dto.LoginResponse;
import com.spring.bookstore.dto.LoginUserDto;
import com.spring.bookstore.entity.Users;
import com.spring.bookstore.security.JwtAuthenticationProvider;
import com.spring.bookstore.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private JwtAuthenticationProvider authenticationProvider;
    private AuthService authService;
    private UserDetailsService userDetailsService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            Users users = this.authService.authenticate(loginUserDto);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(users.getEmail());
            String token = this.authenticationProvider.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(token, this.authenticationProvider.getJwtExpiration()));
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new BadCredentialsException("Email or password is incorrect!"), HttpStatus.UNAUTHORIZED);
        }
    }
}
