package com.spring.placement_management_system.service.impl;

import com.spring.placement_management_system.constant.RoleConstants;
import com.spring.placement_management_system.constant.StatusConstants;
import com.spring.placement_management_system.dto.CurrentUser;
import com.spring.placement_management_system.dto.JobDTO;
import com.spring.placement_management_system.dto.mapper.ApplicationMapper;
import com.spring.placement_management_system.dto.mapper.JobMapper;
import com.spring.placement_management_system.dto.request.ApplyJobRequest;
import com.spring.placement_management_system.dto.response.ApplicationResponse;
import com.spring.placement_management_system.entity.Application;
import com.spring.placement_management_system.entity.Job;
import com.spring.placement_management_system.entity.Student;
import com.spring.placement_management_system.entity.User;
import com.spring.placement_management_system.exception.ApplicationException;
import com.spring.placement_management_system.exception.UserException;
import com.spring.placement_management_system.repository.ApplicationRepository;
import com.spring.placement_management_system.repository.JobRepository;
import com.spring.placement_management_system.repository.StudentRepository;
import com.spring.placement_management_system.service.ApplicationService;
import com.spring.placement_management_system.service.JobService;
import com.spring.placement_management_system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final EligibilityServiceImpl eligibilityService;
    private final ApplicationMapper applicationMapper;
    private final UserService userService;
//    private final JobMapper  jobMapper;
    private final JobService jobService;
    @Override
    @Transactional
    public ApplicationResponse applyForJob(Long jobId)
            throws ApplicationException {

        CurrentUser currentUser = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(currentUser.getUserId())
                .orElseThrow(() -> new ApplicationException("Student not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ApplicationException("Job not found"));
        if (applicationRepository.existsByStudentAndJob(student, job)) {
            Application app = applicationRepository.findByStudentAndJob(student,job).orElseThrow(() -> new ApplicationException("Application not found"));
            if(app.getStatus()==StatusConstants.APPLIED) {
                throw new ApplicationException(
                        "Already applied " +
                                student.getUser().getName() +
                                " to " +
                                job.getCompany().getCompanyName()
                );
            }
            else if(app.getStatus()==StatusConstants.WITHDRAWN && jobRepository.findById(jobId).orElseThrow(() -> new ApplicationException("Job not found")).getApplicationDeadline().isAfter(LocalDate.now())) {
                app.setStatus(StatusConstants.APPLIED);
                app.setUpdatedAt(LocalDateTime.now());
                applicationRepository.save(app);
                return applicationMapper.mapToResponse(app);
            }
             else {
                throw new ApplicationException(
                        "Cannot re-apply after due to application deadline " +
                                student.getUser().getName() +
                                " to " +
                                job.getCompany().getCompanyName()
                );
            }
        }

        if (LocalDate.now().isAfter(job.getApplicationDeadline())) {
            throw new ApplicationException("Application deadline exceeded");
        }

        if (!eligibilityService.isEligible2(
                student.getStudentId(),
                jobId)) {
            throw new ApplicationException("Not eligible for this job");
        }

        LocalDateTime now = LocalDateTime.now();

        Application application = Application.builder()
                .student(student)
                .job(job)
                .status(StatusConstants.APPLIED)
                .appliedAt(now)
                .updatedAt(now)
                .build();

        application = applicationRepository.save(application);

        return applicationMapper.mapToResponse(application);
    }

    @Override
    public ApplicationResponse withdrawApplication(Long applicationId) {
            Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ApplicationException("Application not found with id : " + applicationId));
            CurrentUser currentUser = userService.getCurrentUser();
            Student student = studentRepository.findByUserUserId(currentUser.getUserId()).orElseThrow(() -> new ApplicationException("Student not found"));
            if(!student.getStudentId().equals(application.getStudent().getStudentId())) {
                throw new ApplicationException("Not authorized");
            }
            application.setStatus(StatusConstants.WITHDRAWN);
            application.setUpdatedAt(LocalDateTime.now());
            application = applicationRepository.save(application);
            return applicationMapper.mapToResponse(application);
    }

//    @Override
//    public List<ApplicationResponse> getStudentApplications(StatusConstants status) {
//        CurrentUser user = userService.getCurrentUser();
//        Student student = studentRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new ApplicationException("Student not found for user id : " + user.getUserId()));
//            List<Application> res = applicationRepository.findByStudentStudentId(student.getStudentId()).orElseThrow(() -> new ApplicationException("No applications found for student with id : " +
//                    student.getStudentId()));
//            return res.stream().map(applicationMapper::mapToResponse).toList();
//
//    }

    @Override
    public List<ApplicationResponse> getStudentApplications(
            StatusConstants status
    ) {

        CurrentUser currentUser = userService.getCurrentUser();

        Student student = studentRepository
                .findByUserUserId(currentUser.getUserId())
                .orElseThrow(() -> new UserException("Student not found"));

        List<Application> applications;

        if (status == null) {
            applications = applicationRepository
                    .findByStudentStudentId(student.getStudentId()).orElseThrow(() -> new ApplicationException("No applications found"));
        } else {
            applications = applicationRepository
                    .findByStudentStudentIdAndStatus(
                            student.getStudentId(),
                            status
                    ).orElseThrow(() -> new ApplicationException("No applications found with status : " + status));
        }

        return applications.stream()
                .map(applicationMapper::mapToResponse)
                .toList();
    }



    @Override
    public List<ApplicationResponse> getJobApplications(Long jobId) {
        if(jobRepository.existsById(jobId)) {
            List<Application> res = applicationRepository.findByJobJobId(jobId).orElseThrow(() -> new ApplicationException("No applications found for job with id : " + jobId));
            return res.stream().map(applicationMapper::mapToResponse).toList();
        }
        else {
            throw new ApplicationException("Job not found");
        }
    }


    @Override
    public List<JobDTO> getAllAvailableJobs() {
        return jobService.getAllAvailableJobs();
    }

    @Override
    public ApplicationResponse getApplicationById(Long applicationId) {
        CurrentUser  currentUser = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(currentUser.getUserId()).orElseThrow(() -> new ApplicationException("Student not found"));
        Application application = applicationRepository.findById(applicationId).orElseThrow(()->new ApplicationException("Application not found"));
        if(!Objects.equals(application.getStudent().getStudentId(), student.getStudentId())) {
            throw new ApplicationException("Not authorized");
        }
        return applicationMapper.mapToResponse(application);
    }

    @Override
    public StatusConstants getApplicationStatus(Long jobId) {
        CurrentUser currentUser = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(currentUser.getUserId()).orElseThrow(() -> new ApplicationException("Student not found"));
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ApplicationException("Job not found"));
        Optional<Application> application = applicationRepository.findByStudentAndJob(student, job);
        if (application.isEmpty()) {
            return StatusConstants.NOT_APPLIED;
        }
        else{
            return application.get().getStatus();
        }
    }

    @Override
    public List<ApplicationResponse> getAllApplications() {
        return applicationRepository.findAll().stream().map(applicationMapper::mapToResponse).toList();
    }

    @Override
    public StatusConstants updateApplicationStatus(Long applicationId, StatusConstants status) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ApplicationException("Application not found with id : " + applicationId));
        application.setStatus(status);
        application = applicationRepository.save(application);
        return application.getStatus();
    }

}
