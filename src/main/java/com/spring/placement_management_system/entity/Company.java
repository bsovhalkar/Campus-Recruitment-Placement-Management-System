package com.spring.placement_management_system.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(nullable = false)
    private String companyName;

    private String email;

    private String website;

    private String location;

    @OneToMany(mappedBy = "company")
    private List<Job> jobs;

    @Column(length = 1000)
    private String description;
}