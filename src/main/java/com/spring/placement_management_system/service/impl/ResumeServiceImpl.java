package com.spring.placement_management_system.service.impl;


import com.spring.placement_management_system.constant.RoleConstants;
import com.spring.placement_management_system.dto.CurrentUser;
import com.spring.placement_management_system.dto.ResumeDTO;
import com.spring.placement_management_system.dto.mapper.ResumeMapper;
import com.spring.placement_management_system.dto.mapper.UserMapper;
import com.spring.placement_management_system.entity.Resume;
import com.spring.placement_management_system.entity.Student;
import com.spring.placement_management_system.exception.EligibilityException;
import com.spring.placement_management_system.exception.ResumeException;
import com.spring.placement_management_system.exception.UserException;
import com.spring.placement_management_system.repository.ResumeRepository;
import com.spring.placement_management_system.repository.StudentRepository;
import com.spring.placement_management_system.repository.UserRepository;
import com.spring.placement_management_system.service.ResumeService;
import com.spring.placement_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ResumeServiceImpl
        implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final StudentRepository studentRepository;
    private final ResumeMapper resumeMapper;
    private final UserRepository userRepository;
    private final UserService  userService;
    private final String UPLOAD_DIR =
            "uploads/resumes/";

    @Override
    public ResumeDTO uploadResume(
            Long studentId,
            MultipartFile file) {

        try {
            Student student =
                    studentRepository
                            .findById(studentId)
                            .orElseThrow(() ->
                                    new UserException(
                                            "Student not found"));

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + file.getOriginalFilename();

            Path path =
                    Paths.get(UPLOAD_DIR + fileName);

            Files.createDirectories(
                    path.getParent());

            Files.write(path,
                    file.getBytes());

            Resume resume =
                    resumeRepository
                            .findByStudentStudentId(
                                    studentId)
                            .orElse(new Resume());

            resume.setStudent(student);
            resume.setFileName(fileName);
            resume.setFilePath(path.toString());
            resume.setUploadDate(
                    LocalDateTime.now());

            Resume savedResume = resumeRepository.save(resume);
            return resumeMapper.toDTO(savedResume);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Resume upload failed", e);
        }
    }

    @Override
    public ResumeDTO uploadMyResume(MultipartFile file) {
        CurrentUser currentUser = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(currentUser.getUserId()).orElseThrow(() -> new UserException("Student not found"));
        try {

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + file.getOriginalFilename();

            Path path =
                    Paths.get(UPLOAD_DIR + fileName);

            Files.createDirectories(
                    path.getParent());

            Files.write(path,
                    file.getBytes());

            Resume resume =
                    resumeRepository
                            .findByStudentStudentId(
                                    student.getStudentId())
                            .orElse(new Resume());

            resume.setStudent(student);
            resume.setFileName(fileName);
            resume.setFilePath(path.toString());
            resume.setUploadDate(
                    LocalDateTime.now());

            Resume savedResume = resumeRepository.save(resume);
            return resumeMapper.toDTO(savedResume);

        } catch (Exception e) {
//            e.printStackTrace();
            throw new RuntimeException("Resume upload failed", e);
        }
    }

    @Override
    public ResumeDTO getResume(Long studentId) {

        CurrentUser user = userService.getCurrentUser();

        if (RoleConstants.STUDENT.name().equals(user.getRole()) && !user.getUserId().equals(studentId)) {
            throw new RuntimeException("NOT AUTHORIZED");

        }

        Resume resume = resumeRepository
                .findByStudentStudentId(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Resume not found"));
        return resumeMapper.toDTO(resume);
    }

    @Override
    public ResumeDTO getMyResume() {
        CurrentUser  currentUser = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(currentUser.getUserId()).orElseThrow(() -> new UserException("Student not found"));
        Resume resume = resumeRepository.findByStudentStudentId(student.getStudentId())
                .orElseThrow(() ->
                        new ResumeException("Resume not found"));
        return resumeMapper.toDTO(resume);
    }

    @Override
    public void deleteResume(Long resumeId) {
        CurrentUser user = userService.getCurrentUser();
        if(RoleConstants.STUDENT.name().equals(user.getRole())){
            if(resumeRepository.existsByResumeIdAndStudentUserUserId(resumeId, user.getUserId())){
                resumeRepository.deleteById(resumeId);
                return;
            }
            throw new RuntimeException("NOT AUTHORIZED");
        }

        resumeRepository.deleteById(resumeId);
    }

    @Override
    public void deleteMyResume() {
        CurrentUser user = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new UserException("Student not found"));
        Resume resume = resumeRepository.findByStudentStudentId(student.getStudentId()).orElseThrow(() -> new RuntimeException("Resume not found"));
        resumeRepository.delete(resume);
    }

    @Override
    public String status() {
        CurrentUser currentUser =  userService.getCurrentUser();
        Student student = studentRepository.findById(currentUser.getUserId()).orElseThrow(() ->  new UserException("Student not found"));
        if(resumeRepository.existsByStudentStudentId(student.getStudentId())){
            return "Resume uploaded";
        }
        return "No resume uploaded";

    }
}