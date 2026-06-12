package com.spring.placement_management_system.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeResponse {

    private Long resumeId;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadDate;
}