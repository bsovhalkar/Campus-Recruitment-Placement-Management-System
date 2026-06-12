package com.spring.placement_management_system.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resumes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    @OneToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student student;

    private String fileName;

    private String filePath;

    private LocalDateTime uploadDate;
}