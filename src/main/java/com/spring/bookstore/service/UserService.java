package com.spring.bookstore.service;

import com.spring.bookstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    public int getUserId()  {
        UserDetails userDetails = this.getUserDetails();
        return userDetails != null ? this.userRepository.findByEmail(userDetails.getUsername()).get().getUserId() : 0;
    }
}
