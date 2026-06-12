package com.spring.placement_management_system.entity;

import com.spring.placement_management_system.constant.AssessmentStatus;
import com.spring.placement_management_system.constant.AssessmentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "assessments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assessmentId;

    @Column(nullable = false)
    private Long jobId;

    @Column(nullable = false)
    private String assessmentName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssessmentType assessmentType;

    @Column(nullable = false)
    private LocalDate assessmentDate;

    @Column(nullable = false)
    private LocalTime assessmentTime;

    @Column(nullable = false)
    private Integer totalMarks;

    @Column(nullable = false)
    private Double interviewPercentage;

    @Enumerated(EnumType.STRING)
    private AssessmentStatus status;
}