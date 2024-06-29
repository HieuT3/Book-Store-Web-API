package com.spring.bookstore.bootstrap;

import com.spring.bookstore.entity.Role;
import com.spring.bookstore.entity.RoleEnum;
import com.spring.bookstore.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRole();
    }

    private void loadRole() {
        List<RoleEnum> roleEnums = Arrays.asList(RoleEnum.ADMIN, RoleEnum.USER);
        Map<RoleEnum, String> mapDescription = Map.of(
                RoleEnum.ADMIN, "Default administrator role",
                RoleEnum.USER, "Default user role"
        );

        roleEnums.forEach(roleEnum -> {
            this.roleRepository.findByName(roleEnum).ifPresentOrElse(System.out::println,
                    () -> {
                        Role role = new Role();
                        role.setName(roleEnum);
                        role.setDescription(mapDescription.get(roleEnum));

                        this.roleRepository.save(role);
                    });
        });
    }
}
