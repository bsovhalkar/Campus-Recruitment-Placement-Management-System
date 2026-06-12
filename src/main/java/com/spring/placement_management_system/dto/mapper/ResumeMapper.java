package com.spring.placement_management_system.dto.mapper;

import com.spring.placement_management_system.dto.ResumeDTO;
import com.spring.placement_management_system.entity.Resume;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ResumeMapper {

    public ResumeDTO toDTO(Resume resume) {

        if (resume == null) {
            return null;
        }

        ResumeDTO dto = new ResumeDTO();

        dto.setResumeId(resume.getResumeId());
        dto.setFileName(resume.getFileName());
        dto.setFilePath(resume.getFilePath());
        dto.setUploadDate(resume.getUploadDate());

        return dto;
    }
    public List<ResumeDTO> toDTOList(List<Resume> resumes) {
        if (resumes == null) {
            return null;
        }
        return resumes.stream()
                .map(this::toDTO)
                .toList();
    }

}