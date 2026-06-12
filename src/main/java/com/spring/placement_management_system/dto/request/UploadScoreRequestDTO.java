package com.spring.placement_management_system.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadScoreRequestDTO {

    private Long studentId;

    private Double score;
}