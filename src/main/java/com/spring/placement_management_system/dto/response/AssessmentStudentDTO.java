package com.spring.placement_management_system.dto.response;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentStudentDTO {

    private Long studentId;

    private String studentName;

    private Double score;
}