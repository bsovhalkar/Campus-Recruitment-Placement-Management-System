package com.spring.placement_management_system.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<Skill> skills;

    @OneToOne(mappedBy = "student",
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private Resume resume;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    private List<Application> applications;

    private String phone;

    private String department;

    private Double cgpa;

    private Integer graduationYear;

    private String gender;

    private String address;

    private String placementStatus;

    private Integer activeBacklogs;
}