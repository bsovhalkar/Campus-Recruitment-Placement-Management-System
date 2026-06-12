package com.spring.placement_management_system.dto.mapper;


import com.spring.placement_management_system.dto.response.ApplicationResponse;
import com.spring.placement_management_system.entity.Application;
import com.spring.placement_management_system.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {

    public ApplicationResponse mapToResponse(
            Application application
    ) {

        return ApplicationResponse
                .builder()
                .applicationId(application.getApplicationId())
                .studentId(application.getStudent().getStudentId())
                .studentName(application.getStudent().getUser().getName())
                .jobId(application.getJob().getJobId())
                .jobTitle(application.getJob().getTitle())
                .companyName(application.getJob().getCompany().getCompanyName())
                .status(application.getStatus().name())
                .appliedAt(application.getAppliedAt())
                .remarks(application.getRemarks())
                .build();
    }

//    public Application toEntity()
}