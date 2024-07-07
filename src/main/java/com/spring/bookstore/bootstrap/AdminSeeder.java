package com.spring.bookstore.bootstrap;

import com.spring.bookstore.entity.Role;
import com.spring.bookstore.entity.RoleEnum;
import com.spring.bookstore.entity.Users;
import com.spring.bookstore.repository.RoleRepository;
import com.spring.bookstore.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private Dotenv dotenv;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadAdministrator();
    }

    private void loadAdministrator() {
        Role role = this.roleRepository.findByName(RoleEnum.ADMIN).orElseThrow(
                () -> new EntityNotFoundException("Role not found!")
        );

        String email = this.dotenv.get("admin.email");
        String password = this.dotenv.get("admin.password");
        String fullName = this.dotenv.get("admin.fullName");

        Optional<Users> optionalUsers = this.userRepository.findByEmail(email);
        if(optionalUsers.isPresent()) {
            return;
        }
        Users newAdmin = new Users();
        newAdmin.setEmail(email);
        newAdmin.setPassword(this.passwordEncoder.encode(password));
        newAdmin.setFullName(fullName);
        newAdmin.setEnabled(true);
        newAdmin.setRole(role);

        this.userRepository.save(newAdmin);
    }
}
