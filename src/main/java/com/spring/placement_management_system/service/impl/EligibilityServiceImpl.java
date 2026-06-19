package com.spring.placement_management_system.service.impl;

import com.spring.placement_management_system.constant.RoleConstants;
import com.spring.placement_management_system.dto.CurrentUser;
import com.spring.placement_management_system.entity.Job;
import com.spring.placement_management_system.entity.Student;
import com.spring.placement_management_system.exception.EligibilityException;
import com.spring.placement_management_system.exception.JobException;
import com.spring.placement_management_system.exception.UserException;
import com.spring.placement_management_system.repository.JobRepository;
import com.spring.placement_management_system.repository.StudentRepository;
import com.spring.placement_management_system.service.EligibilityService;
import com.spring.placement_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EligibilityServiceImpl
        implements EligibilityService {
    private final JobRepository jobRepository;
    private final StudentRepository studentRepository;
    private final UserService userService;
    @Override
    public boolean isEligible(
            Long jobId) {
        CurrentUser currentUser = userService.getCurrentUser();

        Student student = studentRepository.findByUserUserId(currentUser.getUserId())
                .orElseThrow(() ->
                        new UserException("Student not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new JobException("Job not found"));

        if (student.getCgpa() < job.getMinimumCgpa()) {
            return false;
        }

        if (!student.getGraduationYear()
                .equals(job.getPassingYear())) {
            return false;
        }

        if (!job.getEligibleDepartments()
                .contains(student.getDepartment())) {
            return false;
        }

        if (!job.getBacklogAllowed()
                && student.getActiveBacklogs() > 0) {
            return false;
        }

        if(job.getBacklogAllowed() && student.getActiveBacklogs() > 0) {
            if(student.getActiveBacklogs() > job.getBacklogAllowedCount()) {
                return false;
            }
        }

        return true;
    }
    public boolean isEligible2(
            Long studentId,
            Long jobId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new UserException("Student not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new JobException("Job not found"));

        if (student.getCgpa() < job.getMinimumCgpa()) {
            return false;
        }

        if (!student.getGraduationYear()
                .equals(job.getPassingYear())) {
            return false;
        }

        if (!job.getEligibleDepartments()
                .contains(student.getDepartment())) {
            return false;
        }

        if (!job.getBacklogAllowed()
                && student.getActiveBacklogs() > 0) {
            return false;
        }

        if(job.getBacklogAllowed() && student.getActiveBacklogs() > 0) {
            if(student.getActiveBacklogs() > job.getBacklogAllowedCount()) {
                return false;
            }
        }

        return true;
    }
}
//    @Override
//    public boolean isEligible(Long studentId, Long jobId) {
//
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() ->
//                        new UserException("Student not found"));
//
//        Job job = jobRepository.findById(jobId)
//                .orElseThrow(() ->
//                        new JobException("Job not found"));
//        if (student.getCgpa() < job.getMinimumCgpa()) {
//            throw new EligibilityException("Minimum CGPA is " + job.getMinimumCgpa());
//        }
//
//        if (!student.getGraduationYear().equals(job.getPassingYear())) {
//            throw new EligibilityException("FAILED : Passing year criteria");
//        }
//
//
//        if (!job.getEligibleDepartments().contains(student.getDepartment())) {
//            throw new EligibilityException("FAILED: Department criteria");
//        }
//        if (!job.getBacklogAllowed() && student.getActiveBacklogs() > 0) {
//            throw new EligibilityException("FAILED: Backlogs not allowed");
//        }
//
//        if (job.getBacklogAllowed() && student.getActiveBacklogs() > 0) {
//            if (student.getActiveBacklogs() > job.getBacklogAllowedCount()) {
//
//                throw new EligibilityException("FAILED: Backlog count exceeded");
//            }
//
//        }
//        return true;
//    }
//}

//    }
