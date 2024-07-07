package com.spring.bookstore.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("mail")
public class MailController {
    private JavaMailSender javaMailSender;

    @GetMapping
    public ResponseEntity<String> sendEmail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("hieucao135kk@gmail.com");
        simpleMailMessage.setTo("hieucb98kk@gmail.com");
        simpleMailMessage.setSubject("Test");
        simpleMailMessage.setText("Test Email");
        this.javaMailSender.send(simpleMailMessage);
        return ResponseEntity.ok("OK");
    }
}
