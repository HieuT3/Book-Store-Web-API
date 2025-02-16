package com.spring.bookstore;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Console;
import java.io.IOException;
import java.util.Map;

public class MainTest {

    public static void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = "123456";
        System.out.println(passwordEncoder.encode(pass));
    }

    public static void main(String[] args) throws Exception {
//        Dotenv dotenv = Dotenv.load();
//        Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
//        cloudinary.config.secure = true;
//        System.out.println(cloudinary.config.cloudName);
//
//        Map params1 = ObjectUtils.asMap(
//                "use_filename", true,
//                "unique_filename", false,
//                "overwrite", true
//        );
//
//        System.out.println(
//                cloudinary.uploader().upload("https://cloudinary-devs.github.io/cld-docs-assets/assets/images/coffee_cup.jpg", params1));
//
//        // Get the asset details
//        Map params2 = ObjectUtils.asMap(
//                "quality_analysis", true
//        );
//
//        System.out.println(
//                cloudinary.api().resource("coffee_cup", params2));
//        testPasswordEncoder();
        int num = 10;
        Integer x = num;
        System.out.println();
    }
}
