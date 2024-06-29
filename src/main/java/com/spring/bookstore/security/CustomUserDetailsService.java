package com.spring.bookstore.security;

import com.spring.bookstore.entity.Users;
import com.spring.bookstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = this.userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User with email " + email + " not found")
        );

        Set<GrantedAuthority> authorities = this.getAuthorities(users);

        return User.builder()
                .username(email)
                .password(users.getPassword())
                .authorities(authorities)
                .build();
    }

    private Set<GrantedAuthority> getAuthorities(Users users) {
        return Stream.of(users.getRole())
                .map((role) -> new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toSet());
    }
}
