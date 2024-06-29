package com.spring.bookstore.bootstrap;

import com.spring.bookstore.entity.Role;
import com.spring.bookstore.entity.RoleEnum;
import com.spring.bookstore.entity.Users;
import com.spring.bookstore.repository.RoleRepository;
import com.spring.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${admin.email}")
    private String email;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadAdministrator();
    }

    private void loadAdministrator() {
        Role role = this.roleRepository.findByName(RoleEnum.ADMIN).orElseThrow(
                () -> new EntityNotFoundException("Role not found!")
        );

        Optional<Users> optionalUsers = this.userRepository.findByEmail(this.email);
        if(optionalUsers.isPresent()) {
            return;
        }
        Users newAdmin = new Users();
        newAdmin.setEmail(this.email);
        newAdmin.setPassword("123456");
        newAdmin.setFullName("Cao Bá Hiếu");
        newAdmin.setRole(role);

        this.userRepository.save(newAdmin);
    }
}
