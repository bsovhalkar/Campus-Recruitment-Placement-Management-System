package com.spring.placement_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.placement_management_system.constant.RoleConstants;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Student student;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleConstants role;

    private String status; // ACTIVE, INACTIVE

    private LocalDateTime createdAt;
}
