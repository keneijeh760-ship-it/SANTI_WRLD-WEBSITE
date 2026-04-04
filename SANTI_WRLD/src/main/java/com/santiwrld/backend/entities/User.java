package com.santiwrld.backend.entities;

import com.santiwrld.backend.config.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends AuditEntity {
    @Id
    @SequenceGenerator(name = "user",
    sequenceName = "user",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user")
    @Column(unique = true, nullable = false, name = "user_id")
    private Long Id;
    @Column(unique = false, nullable = false, name = "firstname")
    private String firstName;
    @Column(unique = false, nullable = false, name = "lastname")
    private String lastName;
    @Column(unique = true, nullable = false, name = "email")
    private String email;
    @Column(unique = true, nullable = true, name = "phoneNumber")
    private int phoneNumber;
    @Column(name = "password", nullable = false, unique = true)
    private String password;
    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.CUSTOMER;

}
