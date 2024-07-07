package com.spring.bookstore.service;

import com.spring.bookstore.entity.Users;
import com.spring.bookstore.entity.VerificationToken;
import com.spring.bookstore.repository.UserRepository;
import com.spring.bookstore.repository.VerificationTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;

    public UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    public int getUserId()  {
        UserDetails userDetails = this.getUserDetails();
        return userDetails != null ? this.userRepository.findByEmail(userDetails.getUsername()).get().getUserId() : 0;
    }

    public VerificationToken getVerificationTokenByToken(String token) {
        return this.verificationTokenRepository.findByToken(token).orElseThrow(
                EntityNotFoundException::new
        );
    }

    public VerificationToken saveVerificationToken(Users users, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUsers(users);
        verificationToken.setToken(token);
        verificationToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600000 * 24));
        return this.verificationTokenRepository.save(verificationToken);
    }
}
