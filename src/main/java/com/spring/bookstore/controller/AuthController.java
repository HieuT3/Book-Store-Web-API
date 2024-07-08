package com.spring.bookstore.controller;

import com.spring.bookstore.dto.CustomerProfileDto;
import com.spring.bookstore.dto.LoginResponseDto;
import com.spring.bookstore.dto.LoginUserDto;
import com.spring.bookstore.entity.Users;
import com.spring.bookstore.entity.VerificationToken;
import com.spring.bookstore.repository.UserRepository;
import com.spring.bookstore.security.JwtAuthenticationProvider;
import com.spring.bookstore.service.AuthService;
import com.spring.bookstore.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private JwtAuthenticationProvider authenticationProvider;
    private AuthService authService;
    private UserDetailsService userDetailsService;
    private UserService userService;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            Users users = this.authService.authenticate(loginUserDto);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(users.getEmail());
            String token = this.authenticationProvider.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponseDto(token, this.authenticationProvider.getJwtExpiration()));
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new BadCredentialsException("Email or password is incorrect!"), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("registrationConfirmation")
    public ResponseEntity<?> registrationConfirmation(@RequestParam("token") String token) {
        VerificationToken verificationToken = this.userService.getVerificationTokenByToken(token);
        if(verificationToken == null) {
            return new ResponseEntity<>("Invalid Token", HttpStatus.BAD_REQUEST);
        }
        if(verificationToken.getExpiryDate().before(new Date(System.currentTimeMillis()))) {
            return new ResponseEntity<>("Token expired", HttpStatus.BAD_REQUEST);
        }
        Users users = verificationToken.getUsers();
        users.setEnabled(true);
        return ResponseEntity.ok(this.modelMapper.map(this.userRepository.save(users), CustomerProfileDto.class));
    }
}
