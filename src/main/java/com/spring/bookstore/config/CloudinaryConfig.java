package com.spring.bookstore.config;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class CloudinaryConfig {

    private Dotenv dotenv;

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(this.dotenv.get("CLOUDINARY_URL"));
        cloudinary.config.secure = true;
        return cloudinary;
    }
}
