package com.spring.placement_management_system.service.impl;

import com.spring.placement_management_system.constant.StatusConstants;
import com.spring.placement_management_system.dto.response.DashboardAnalyticsDTO;
import com.spring.placement_management_system.dto.response.DepartmentPlacementDTO;
import com.spring.placement_management_system.dto.response.StudentPlacementDTO;
import com.spring.placement_management_system.entity.Application;
import com.spring.placement_management_system.entity.Student;
import com.spring.placement_management_system.exception.ApplicationException;
import com.spring.placement_management_system.repository.ApplicationRepository;
import com.spring.placement_management_system.repository.AssessmentRepository;
import com.spring.placement_management_system.repository.CompanyRepository;
import com.spring.placement_management_system.repository.JobRepository;
import com.spring.placement_management_system.repository.StudentRepository;
import com.spring.placement_management_system.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final AssessmentRepository assessmentRepository;

    @Override
    public DashboardAnalyticsDTO getDashboardAnalytics() {

        long totalStudents = studentRepository.count();

        long selectedStudents =
                applicationRepository.countByStatus(
                        StatusConstants.SELECTED
                );

        long rejectedStudents =
                applicationRepository.countByStatus(
                        StatusConstants.REJECTED
                );

        double placementPercentage =
                totalStudents == 0
                        ? 0.0
                        : (selectedStudents * 100.0)
                        / totalStudents;

        return DashboardAnalyticsDTO.builder()
                .totalStudents(totalStudents)
                .totalCompanies(companyRepository.count())
                .totalJobs(jobRepository.count())
                .totalApplications(applicationRepository.count())
                .totalAssessments(assessmentRepository.count())
                .selectedStudents(selectedStudents)
                .rejectedStudents(rejectedStudents)
                .placementPercentage(
                        Math.round(
                                placementPercentage * 100.0
                        ) / 100.0
                )
                .build();
    }

    @Override
    public List<DepartmentPlacementDTO>
    getDepartmentWisePlacement() {

        List<Student> students =
                studentRepository.findAll();

        Map<String, List<Student>> departmentMap =
                students.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Student::getDepartment
                                )
                        );

        List<Application> selectedApplications =
                applicationRepository.findByStatus(
                        StatusConstants.SELECTED
                ).orElseThrow(()->new ApplicationException(
                        "No selected applications found"
                ));

        Set<Long> selectedStudentIds =
                selectedApplications.stream()
                        .map(
                                application ->
                                        application.getStudent()
                                                .getStudentId()
                        )
                        .collect(Collectors.toSet());

        List<DepartmentPlacementDTO> result =
                new ArrayList<>();

        for (Map.Entry<String, List<Student>> entry :
                departmentMap.entrySet()) {

            String department =
                    entry.getKey();

            List<Student> departmentStudents =
                    entry.getValue();

            long totalStudents =
                    departmentStudents.size();

            long selectedStudents =
                    departmentStudents.stream()
                            .filter(
                                    student ->
                                            selectedStudentIds.contains(
                                                    student.getStudentId()
                                            )
                            )
                            .count();

            double placementPercentage =
                    totalStudents == 0
                            ? 0.0
                            : (selectedStudents * 100.0)
                            / totalStudents;

            result.add(
                    DepartmentPlacementDTO.builder()
                            .department(department)
                            .totalStudents(totalStudents)
                            .selectedStudents(selectedStudents)
                            .placementPercentage(
                                    Math.round(
                                            placementPercentage * 100.0
                                    ) / 100.0
                            )
                            .build()
            );
        }

        return result;
    }

    @Override
    public List<StudentPlacementDTO>
    getSelectedStudents() {

        return applicationRepository
                .findByStatus(
                        StatusConstants.SELECTED
                ).orElseThrow(()->new ApplicationException("List is Empty"))
                .stream()
                .map(this::mapToStudentPlacementDTO)
                .toList();
    }

    @Override
    public List<StudentPlacementDTO>
    getRejectedStudents() {

        return applicationRepository
                .findByStatus(
                        StatusConstants.REJECTED
                ).orElseThrow(()->new ApplicationException("List is Empty"))
                .stream()
                .map(this::mapToStudentPlacementDTO)
                .toList();
    }

    private StudentPlacementDTO
    mapToStudentPlacementDTO(
            Application application
    ) {

        Student student =
                application.getStudent();

        return StudentPlacementDTO.builder()
                .studentId(student.getStudentId())
                .name(student.getUser().getName())
                .department(student.getDepartment())
                .cgpa(student.getCgpa())
                .companyName(
                        application.getJob()
                                .getCompany()
                                .getCompanyName()
                )
                .build();
    }
}