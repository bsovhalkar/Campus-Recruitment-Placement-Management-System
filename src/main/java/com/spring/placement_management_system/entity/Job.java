package com.spring.placement_management_system.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    private String title;

    private String description;

    private Double packageOffered;

    private Double minimumCgpa;

    private Integer vacancies;

    private LocalDate applicationDeadline;

    private String location;


    private Integer passingYear;

    private Boolean backlogAllowed;

    private Integer backlogAllowedCount;


    private String eligibleDepartments;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(
            mappedBy = "job",
            cascade = CascadeType.ALL
    )
    private List<Application> applications;

    private LocalDateTime createdAt;
}