package com.spring.placement_management_system.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResumeDTO {

    private Long resumeId;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadDate;
}