package com.spring.placement_management_system.entity;
import com.spring.placement_management_system.constant.StatusConstants;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "applications",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "student_id",
                                "job_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    @JsonBackReference
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "job_id",
            nullable = false
    )
    private Job job;

    @Enumerated(EnumType.STRING)
    private StatusConstants status;

    private LocalDateTime appliedAt;

    private LocalDateTime updatedAt;

    private String remarks;

    private Double score;

    @Column(name = "application_rank")
    private Integer rank;

    private LocalDateTime shortlistedAt;
}

