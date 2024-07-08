package com.spring.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @JsonIgnore
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<PasswordResetToken> tokens;
}
