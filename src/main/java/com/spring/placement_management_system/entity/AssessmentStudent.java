package com.spring.placement_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assessment_students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;

    private Integer scoreRank;

    private Boolean shortlisted;

    /*
     * Many students belong to one assessment
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "assessment_id",
            nullable = false)
    private Assessment assessment;

    /*
     * Student who appeared in assessment
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "student_id",
            nullable = false)
    private Student student;
}