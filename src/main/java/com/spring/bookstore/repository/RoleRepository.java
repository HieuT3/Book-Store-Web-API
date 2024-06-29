package com.spring.bookstore.repository;

import com.spring.bookstore.entity.Role;
import com.spring.bookstore.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleEnum name);
}
