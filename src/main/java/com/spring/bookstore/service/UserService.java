package com.spring.bookstore.service;

import com.spring.bookstore.dto.ResetPasswordDto;
import com.spring.bookstore.entity.PasswordResetToken;
import com.spring.bookstore.entity.Users;
import com.spring.bookstore.entity.VerificationToken;
import com.spring.bookstore.repository.PasswordResetTokenRepository;
import com.spring.bookstore.repository.UserRepository;
import com.spring.bookstore.repository.VerificationTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;

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
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        return this.verificationTokenRepository.save(verificationToken);
    }

    public void resetPassword(String email) {
        Users users = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("The user with email " + email + " does not exist!"));
        PasswordResetToken passwordResetToken = this.createPasswordResetToken(users);

        String token = passwordResetToken.getToken();
        String message = "Please click the link to reset your password http://localhost:8080/auth/change-password?token=" + token;
        String to = users.getEmail();
        String subject = "Reset Password";

        this.mailService.sendMail(to, subject, message);
    }

    public PasswordResetToken createPasswordResetToken(Users users) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUsers(users);
        passwordResetToken.setExpiredDate(LocalDateTime.now().plusHours(24));
        return this.passwordResetTokenRepository.save(passwordResetToken);
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = this.passwordResetTokenRepository.findByToken(token).orElse(null);
        if (passwordResetToken == null) return null;
        if (passwordResetToken.getExpiredDate().isBefore(LocalDateTime.now())) return "Expired";
        return "ValidToken";
    }

    public Users savePassword(ResetPasswordDto resetPasswordDto) {
        String email = resetPasswordDto.getEmail();
        Users users = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " does not exist!"));
        users.setPassword(this.passwordEncoder.encode(resetPasswordDto.getPassword()));
        return this.userRepository.save(users);
    }
}
