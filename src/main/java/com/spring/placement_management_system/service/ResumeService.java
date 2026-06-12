package com.spring.placement_management_system.service;


import com.spring.placement_management_system.dto.ResumeDTO;
//import com.spring.placement_management_system.entity.Resume;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {

    ResumeDTO uploadResume(Long studentId,
                           MultipartFile file);
    ResumeDTO uploadMyResume(MultipartFile file);

    ResumeDTO getResume(Long studentId);
    ResumeDTO getMyResume();

    void deleteResume(Long resumeId);
    void deleteMyResume();

    String status();
}