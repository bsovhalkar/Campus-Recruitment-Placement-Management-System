package com.spring.placement_management_system.service.impl;

import com.spring.placement_management_system.constant.AssessmentStatus;
import com.spring.placement_management_system.constant.AssessmentType;
//import com.spring.placement_management_system.constant.PlacementStatus;
import com.spring.placement_management_system.constant.StatusConstants;
import com.spring.placement_management_system.dto.CurrentUser;
import com.spring.placement_management_system.dto.StudentDTO;
import com.spring.placement_management_system.dto.mapper.AssessmentMapper;
import com.spring.placement_management_system.dto.mapper.StudentMapper;
import com.spring.placement_management_system.dto.mapper.assessment__student__mapper;
import com.spring.placement_management_system.dto.request.ScheduleAssessmentRequestDTO;
import com.spring.placement_management_system.dto.request.UploadScoreRequestDTO;
import com.spring.placement_management_system.dto.response.*;
import com.spring.placement_management_system.entity.*;
import com.spring.placement_management_system.exception.ApplicationException;
import com.spring.placement_management_system.exception.AssessmentException;
import com.spring.placement_management_system.exception.JobException;
import com.spring.placement_management_system.exception.UserException;
import com.spring.placement_management_system.repository.*;
import com.spring.placement_management_system.service.AssessmentService;
import com.spring.placement_management_system.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;

    private final AssessmentStudentRepository
            assessmentStudentRepository;

    private final ApplicationRepository
            applicationRepository;

    private final JobRepository
            jobRepository;

    private final AssessmentMapper
            assessmentMapper;
    private final UserService  userService;
    private final StudentRepository studentRepository;
    private final assessment__student__mapper assessment_student_mapper;

    @Override
    public AssessmentAdminResponseDTO scheduleAssessment(
            ScheduleAssessmentRequestDTO request) {

        Job job = jobRepository.findById(
                        request.getJobId())
                .orElseThrow(() ->
                        new JobException("Job not found"));

        boolean assessmentExists =
                assessmentRepository
                        .existsByJobIdAndAssessmentDateAndAssessmentTimeAndAssessmentType(
                                job.getJobId(),
                                request.getAssessmentDate(),
                                request.getAssessmentTime(),
                                request.getAssessmentType()
                        );

        if (assessmentExists) {
            throw new AssessmentException(
                    "An assessment is already scheduled for this job at "
                            + request.getAssessmentDate()
                            + " "
                            + request.getAssessmentTime());
        }

        Assessment assessment =
                Assessment.builder()
                        .jobId(job.getJobId())
                        .assessmentName(
                                request.getAssessmentName())
                        .assessmentType(
                                request.getAssessmentType())
                        .assessmentDate(
                                request.getAssessmentDate())
                        .assessmentTime(
                                request.getAssessmentTime())
                        .totalMarks(
                                request.getTotalMarks())
                        .interviewPercentage(
                                request.getInterviewPercentage())
                        .status(
                                AssessmentStatus.SCHEDULED)
                        .build();

        Assessment savedAssessment =
                assessmentRepository.save(
                        assessment);

        assignStudents(savedAssessment);

        return assessmentMapper.mapToAdminResponse(savedAssessment,job);
    }

    private void assignStudents(
            Assessment assessment) {

        StatusConstants requiredStatus;

        if (assessment.getAssessmentType()
                == AssessmentType.TEST) {

            requiredStatus =
                    StatusConstants.SHORTLISTED_FOR_TEST;

        } else {

            requiredStatus =
                    StatusConstants.SHORTLISTED_FOR_INTERVIEW;
        }

        List<Application> applications =
                applicationRepository
                        .findByJobJobIdAndStatus(
                                assessment.getJobId(),
                                requiredStatus).orElseThrow(() -> new ApplicationException("No applications found with required status for this job"));

        List<AssessmentStudent> students =
                new ArrayList<>();

        for (Application application :
                applications) {

            AssessmentStudent assessmentStudent =
                    AssessmentStudent.builder()
                            .assessment(
                                    assessment)
                            .student(
                                    application.getStudent())
                            .score(0.0)
                            .scoreRank(null)
                            .shortlisted(false)
                            .build();

            students.add(
                    assessmentStudent);

            if (assessment.getAssessmentType()
                    == AssessmentType.TEST) {

                application.setStatus(
                        StatusConstants.TEST_SCHEDULED);
            }
            else{
                application.setStatus(
                        StatusConstants.INTERVIEW_SCHEDULED
                );
            }

        }

        assessmentStudentRepository.saveAll(
                students);
    }

    @Override
    public void uploadScores(
            Long assessmentId,
            List<UploadScoreRequestDTO> scores) {

        Assessment assessment =
                assessmentRepository.findById(
                                assessmentId)
                        .orElseThrow(() ->
                                new AssessmentException(
                                        "Assessment not found"));

        for (UploadScoreRequestDTO dto :
                scores) {

            AssessmentStudent student =
                    assessmentStudentRepository
                            .findByAssessmentAssessmentIdAndStudentStudentId(
                                    assessmentId,
                                    dto.getStudentId())
                            .orElseThrow(() ->
                                    new UserException(
                                            "Student not found in assessment"));

            student.setScore(
                    dto.getScore());

            assessmentStudentRepository.save(
                    student);
        }

        generateRanking(assessment);

        if (assessment.getAssessmentType()
                == AssessmentType.TEST) {

            processTestResults(
                    assessment);

        } else {

            processInterviewResults(
                    assessment);
        }

        assessment.setStatus(
                AssessmentStatus.COMPLETED);

        assessmentRepository.save(
                assessment);
    }

    @Override
    public List<AssessmentResponseDTO> getMyAssessments() {

        CurrentUser user = userService.getCurrentUser();

        Student student = studentRepository
                .findByUserUserId(user.getUserId())
                .orElseThrow(() -> new UserException("Student not found"));

        return assessmentStudentRepository
                .findByStudentStudentId(student.getStudentId())
                .stream()
                .map(as -> {
                    Assessment assessment = as.getAssessment();

                    Job job = jobRepository.findById(
                                    assessment.getJobId())
                            .orElseThrow(() ->
                                    new JobException("Job not found"));

                    return assessmentMapper.mapToResponse(
                            assessment,
                            job,
                            as.getScore()
                    );
                })
                .toList();
    }
    @Override
    public AssessmentResponseDTO getAssessmentById(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new AssessmentException("Assessment not found with id : " + assessmentId));
        CurrentUser user = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new UserException("Student not found"));
        Job job = jobRepository.findById(assessment.getJobId()).orElseThrow(() -> new JobException("Job not found"));
        AssessmentStudent assessmentStudent = assessmentStudentRepository.findByAssessmentAssessmentIdAndStudentStudentId(assessmentId, student.getStudentId())
                .orElseThrow(() -> new AssessmentException("Assessment not found for student with id : " + student.getStudentId()));
        return assessmentMapper.mapToResponse(assessment, job, assessmentStudent.getScore());


    }

    @Override
    public Double getAssessmentScore(Long assessmentId) {
        CurrentUser user = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new UserException("Student not found"));
        AssessmentStudent assessmentStudent = assessmentStudentRepository.findByAssessmentAssessmentIdAndStudentStudentId(assessmentId, student.getStudentId())
                .orElseThrow(() -> new AssessmentException("Assessment not found for student with id : " + student.getStudentId()));
        return assessmentStudent.getScore();
    }

    @Override
    public Boolean isEligibleForInterview(Long assessmentId) {
        return null;


    }

    @Override
    public List<AssessmentResponseDTO> getUpcomingAssessments() {

        CurrentUser user = userService.getCurrentUser();

        Student student = studentRepository
                .findByUserUserId(user.getUserId())
                .orElseThrow(() -> new UserException("Student not found"));

        return assessmentStudentRepository
                .findByStudentStudentIdAndAssessment_StatusAndAssessment_AssessmentDateGreaterThanEqual(
                        student.getStudentId(),
                        AssessmentStatus.SCHEDULED,
                        LocalDate.now()
                )
                .stream()
                .map(as -> {
                    Assessment assessment = as.getAssessment();

                    Job job = jobRepository.findById(
                                    assessment.getJobId())
                            .orElseThrow(() ->
                                    new JobException("Job not found"));

                    return assessmentMapper.mapToResponse(
                            assessment,
                            job,
                            as.getScore()
                    );
                })
                .toList();
    }

    @Override
    public AssessmentDashboardDTO getAssessmentDashboard() {
        CurrentUser user = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new UserException("Student not found"));
        List<AssessmentStudent> assessmentStudentList = assessmentStudentRepository.findByStudentStudentId(student.getStudentId());
        int totalScore = assessmentStudentList.size();
        int completed = 0;
        int upcoming = 0;
        for(AssessmentStudent assessmentStudent : assessmentStudentList) {
            if(assessmentStudent.getAssessment().getStatus() == AssessmentStatus.COMPLETED) {
                completed++;
            }
            else {
                upcoming++;
            }
        }
        return AssessmentDashboardDTO.builder()
                .totalAssessments(totalScore)
                .completed(completed)
                .upcoming(upcoming)
                .build();
    }

    @Override
    public List<AssessmentResponseDTO> getAssessmentsByJobId(Long jobId) {
        return assessmentRepository.findByJobId(jobId)
                .stream()
                .map(assessment -> {
                    Job job = jobRepository.findById(assessment.getJobId())
                            .orElseThrow(() -> new JobException("Job not found"));
                    return assessmentMapper.mapToResponse(assessment, job, null);
                })
                .toList();
    }

    @Override
    public List<AssessmentResponseDTO> getAllAssessments() {
        return assessmentRepository.findAll()
                .stream()
                .map(assessment -> {
                    Job job = jobRepository.findById(assessment.getJobId())
                            .orElseThrow(() -> new JobException("Job not found"));
                    return assessmentMapper.mapToResponse(assessment, job, null);
                })
                .toList();
    }

    @Override
    public void deleteAssessment(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new AssessmentException("Assessment not found with id : " + assessmentId));

        assessmentStudentRepository.deleteAll(
                assessmentStudentRepository.findByAssessmentAssessmentId(assessmentId));

        assessmentRepository.delete(assessment);
    }

    @Override
    public AssessmentAdminResponseDTO getAssessmentByAssessmentId(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new AssessmentException("Assessment not found with id : " + assessmentId));
        Job job = jobRepository.findById(assessment.getJobId())
                .orElseThrow(() -> new JobException("Job not found with id : " + assessment.getJobId()));
        return assessmentMapper.mapToAdminResponse(assessment,job);
    }

    @Override
    public List<AssessmentAdminResponseDTO> adminGetAllAssessments() {
        return assessmentRepository.findAll()
                .stream()
                .map(assessment -> {
                    Job job = jobRepository.findById(assessment.getJobId())
                            .orElseThrow(() -> new JobException("Job not found with id : " + assessment.getJobId()));
                    return assessmentMapper.mapToAdminResponse(assessment,job);
                })
                .toList();
    }

    @Override
    public List<AssessmentAdminResponseDTO> adminGetAllAssessmentsByJobId(Long jobId) {
        if(!jobRepository.existsById(jobId)) {
            throw new JobException("Job not found with id : " + jobId);
        }
        return assessmentRepository.findByJobId(jobId)
                .stream()
                .map(assessment -> {
                    Job job = jobRepository.findById(assessment.getJobId())
                            .orElseThrow(() -> new JobException("Job not found with id : " + assessment.getJobId()));
                    return assessmentMapper.mapToAdminResponse(assessment,job);
                })
                .toList();
    }

    @Override
    public List<AssessmentStudentDTO> getStudentsByAssessmentId(Long assessmentId) {
        return assessmentStudentRepository
                .findByAssessmentAssessmentId(assessmentId)
                .stream()
                .map(assessment_student_mapper::toDTO)
                .toList();
    }

    @Override
    public void uploadScoresCsv(
            Long assessmentId,
            MultipartFile file
    ) throws IOException {

        List<UploadScoreRequestDTO> scores =
                new ArrayList<>();

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                file.getInputStream()
                        )
                );

        String line;

        boolean firstRow = true;

        while ((line = reader.readLine()) != null) {

            if (firstRow) {
                firstRow = false;
                continue;
            }

            String[] data = line.split(",");

            if (data.length < 2) {
                continue;
            }

            UploadScoreRequestDTO dto =
                    new UploadScoreRequestDTO();

            dto.setStudentId(
                    Long.parseLong(
                            data[0].trim()
                    )
            );

            dto.setScore(
                    Double.parseDouble(
                            data[data.length - 1]
                                    .trim()
                    )
            );

            scores.add(dto);
        }

        uploadScores(
                assessmentId,
                scores
        );
    }

    private void generateRanking(
            Assessment assessment) {

        List<AssessmentStudent> students =
                assessmentStudentRepository
                        .findByAssessmentAssessmentId(
                                assessment.getAssessmentId());

        students.sort(
                Comparator.comparing(
                                AssessmentStudent::getScore)
                        .reversed());

        int rank = 1;

        for (AssessmentStudent student :
                students) {

            student.setScoreRank(
                    rank++);

            assessmentStudentRepository.save(
                    student);
        }
    }

    private void processTestResults(
            Assessment assessment) {

        Job job =
                jobRepository.findById(
                                assessment.getJobId())
                        .orElseThrow(() ->
                                new JobException(
                                        "Job not found"));

        int qualifiedCount =
                job.getVacancies() * 10;

        List<AssessmentStudent> students =
                assessmentStudentRepository
                        .findByAssessmentAssessmentId(
                                assessment.getAssessmentId());

        students.sort(
                Comparator.comparing(
                                AssessmentStudent::getScore)
                        .reversed());

        qualifiedCount =
                Math.min(
                        qualifiedCount,
                        students.size());

        for (int i = 0;
             i < students.size();
             i++) {

            AssessmentStudent student =
                    students.get(i);

            if (i < qualifiedCount) {

                student.setShortlisted(
                        true);

                updateApplicationStatus(
                        assessment.getJobId(),
                        student.getStudent()
                                .getStudentId(),
                        StatusConstants.SHORTLISTED_FOR_INTERVIEW);

            } else {

                student.setShortlisted(
                        false);

                updateApplicationStatus(
                        assessment.getJobId(),
                        student.getStudent()
                                .getStudentId(),
                        StatusConstants.REJECTED);
            }

            assessmentStudentRepository.save(
                    student);
        }
    }

    private void processInterviewResults(
            Assessment assessment) {

        Job job =
                jobRepository.findById(
                                assessment.getJobId())
                        .orElseThrow(() ->
                                new JobException(
                                        "Job not found"));

        int vacancies =
                job.getVacancies();

        List<AssessmentStudent> students =
                assessmentStudentRepository
                        .findByAssessmentAssessmentId(
                                assessment.getAssessmentId());

        students.sort(
                Comparator.comparing(
                                AssessmentStudent::getScore)
                        .reversed());

        vacancies =
                Math.min(
                        vacancies,
                        students.size());

        for (int i = 0;
             i < students.size();
             i++) {

            AssessmentStudent student =
                    students.get(i);

            Application application =
                    applicationRepository
                            .findByJobJobIdAndStudentStudentId(
                                    assessment.getJobId(),
                                    student.getStudent()
                                            .getStudentId())
                            .orElseThrow(() ->
                                    new ApplicationException(
                                            "Application not found"));

            if (i < vacancies) {

                student.setShortlisted(
                        true);

                application.setStatus(
                        StatusConstants.SELECTED);

                application.getStudent()
                        .setPlacementStatus("PLACED");

            } else {

                student.setShortlisted(
                        false);

                application.setStatus(
                        StatusConstants.REJECTED);
            }

            assessmentStudentRepository.save(
                    student);
        }
    }

    private void updateApplicationStatus(
            Long jobId,
            Long studentId,
            StatusConstants status) {

        Application application =
                applicationRepository
                        .findByJobJobIdAndStudentStudentId(
                                jobId,
                                studentId)
                        .orElseThrow(() ->
                                new ApplicationException(
                                        "Application not found"));

        application.setStatus(
                status);
    }

    @Override
    public Resource downloadTemplate(
            Long assessmentId
    ) throws IOException {

        List<AssessmentStudentDTO> students =
                getStudentsByAssessmentId(
                        assessmentId
                );

        StringWriter writer =
                new StringWriter();

        writer.append(
                "studentId,studentName,score\n"
        );

        for (
                AssessmentStudentDTO student
                : students
        ) {

            writer.append(
                    String.valueOf(
                            student.getStudentId()
                    )
            );

            writer.append(",");

            writer.append(
                    student.getStudentName()
            );

            writer.append(",");

            writer.append("\n");
        }

        return new ByteArrayResource(
                writer.toString()
                        .getBytes(
                                StandardCharsets.UTF_8
                        )
        );
    }
}