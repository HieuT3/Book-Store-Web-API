package com.spring.bookstore.event;

import com.spring.bookstore.entity.Users;
import com.spring.bookstore.entity.VerificationToken;
import com.spring.bookstore.service.MailService;
import com.spring.bookstore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class TheRegisterListener implements ApplicationListener<OnRegisterCompleteEvent> {

    private UserService userService;
    private MailService mailService;

    @Override
    public void onApplicationEvent(OnRegisterCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegisterCompleteEvent event) {
        Users users = event.getUsers();
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = this.userService.saveVerificationToken(users, token);

        String confirmationURL = event.getAppURL() + "/auth/registrationConfirmation?token=" + token;
        String message = "Click here to confirm: " + "\r\n" + "http://localhost:8080" + confirmationURL;
        String to = users.getEmail();
        String subject = "Registration Confirmation";

        this.mailService.sendMail(to, subject, message);
    }
}
