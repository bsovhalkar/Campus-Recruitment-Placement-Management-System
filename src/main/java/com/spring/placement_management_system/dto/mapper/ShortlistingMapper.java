package com.spring.placement_management_system.dto.mapper;

import com.spring.placement_management_system.dto.response.ShortlistingDTO;
import com.spring.placement_management_system.entity.Application;

public class ShortlistingMapper {

    public static ShortlistingDTO mapToDTO(
            Application application
    ) {

        return ShortlistingDTO.builder()
                .applicationId(application.getApplicationId())
                .studentId(application.getStudent().getStudentId())
                .studentName(application.getStudent()
                        .getUser()
                        .getName())
                .jobId(application.getJob().getJobId())
                .jobTitle(application.getJob().getTitle())
                .cgpa(application.getStudent().getCgpa())
                .skillsCount(
                        application.getStudent()
                                .getSkills()
                                .size()
                )
                .score(application.getScore())
                .rank(application.getRank())
                .build();
    }
}