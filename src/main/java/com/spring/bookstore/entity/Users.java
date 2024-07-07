package com.spring.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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

    @Column(nullable = false)
    protected boolean enabled = false;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "role_id")
    protected Role role;
}
