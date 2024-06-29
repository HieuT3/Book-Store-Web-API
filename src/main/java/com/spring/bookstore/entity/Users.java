package com.spring.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    protected int userId;

    @Column(nullable = false, unique = true, length = 50)
    protected String email;

    @Column(nullable = false)
    protected String password;

    @Column(nullable = false, length = 50)
    protected String fullName;

    @OneToOne
    @JoinColumn(name = "role", referencedColumnName = "role_id", nullable = false)
    protected Role role;
}
