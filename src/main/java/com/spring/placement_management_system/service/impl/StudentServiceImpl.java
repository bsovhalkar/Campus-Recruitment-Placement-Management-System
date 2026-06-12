package com.spring.placement_management_system.service.impl;


import com.spring.placement_management_system.constant.RoleConstants;
import com.spring.placement_management_system.dto.CurrentUser;
import com.spring.placement_management_system.dto.response.ProfileCompletionResponse;
import com.spring.placement_management_system.dto.StudentDTO;
import com.spring.placement_management_system.dto.mapper.ApplicationMapper;
import com.spring.placement_management_system.dto.mapper.ResumeMapper;
import com.spring.placement_management_system.dto.mapper.SkillMapper;
import com.spring.placement_management_system.dto.mapper.StudentMapper;
import com.spring.placement_management_system.dto.request.BulkStudentRequest;
import com.spring.placement_management_system.dto.request.CreateStudentRequest;
import com.spring.placement_management_system.dto.response.MyProfileResponse;
import com.spring.placement_management_system.entity.Student;
import com.spring.placement_management_system.entity.User;
import com.spring.placement_management_system.exception.UserException;
import com.spring.placement_management_system.repository.StudentRepository;
import com.spring.placement_management_system.repository.UserRepository;
import com.spring.placement_management_system.service.StudentService;
import com.spring.placement_management_system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl
        implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final UserService  userService;
    private final ResumeMapper resumeMapper;
    private final SkillMapper skillMapper;
    private final ApplicationMapper  applicationMapper;
    @Override
    public StudentDTO createProfile(CreateStudentRequest request) {
        CurrentUser currentUser = userService.getCurrentUser();
        User user = null;
        if(RoleConstants.ADMIN.name().equals(currentUser.getRole()))  {
            if(request.getUserId()==null) {
                throw new UserException("User ID is required for admin");
            }
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new UserException("User not found"));
        }
        else{
            user = userRepository.findById(currentUser.getUserId())
                    .orElseThrow(() -> new UserException("User not found"));
        }
        User finalUser = user;
        Optional<Student> profile =
                studentRepository.findByUserUserId(user.getUserId());
        System.out.println("request.userId = " + request.getUserId());
        if(profile.isPresent()) {
            throw new UserException("Profile already exists");
        }
        Student student = studentRepository.findByUserUserId(user.getUserId())
                .orElseGet(() -> Student.builder()
                        .user(finalUser)
                        .placementStatus("NOT_PLACED")
                        .build());

        student.setPhone(request.getPhone());
        student.setDepartment(request.getDepartment());
        student.setCgpa(request.getCgpa());
        student.setGraduationYear(request.getGraduationYear());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setActiveBacklogs(request.getActiveBacklogs());

        Student saved = studentRepository.save(student);
        return studentMapper.toDTO(saved);
    }

    private Student getProfile2(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new UserException("Student not found"));
    }

    @Override
    public StudentDTO getProfile(Long studentId) {
        CurrentUser currentUser = userService.getCurrentUser();

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new UserException("Student not found"));
        if(RoleConstants.STUDENT.name().equals(currentUser.getRole())
                && !currentUser.getUserId()
                .equals(student.getUser().getUserId())) {

            throw new UserException("NOT AUTHORIZED");
        }
        return studentMapper.toDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        CurrentUser currentUser = userService.getCurrentUser();
        if(RoleConstants.STUDENT.name().equals(currentUser.getRole())) {
            throw new UserException("NOT AUTHORIZED");
        }
        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(studentMapper::toDTO)
                .toList();
    }

    @Override
    public StudentDTO updateProfile(Long studentId,
                                 CreateStudentRequest request) {

        Student student = getProfile2(studentId);

        CurrentUser currentUser = userService.getCurrentUser();
        if(RoleConstants.STUDENT.name().equals(currentUser.getRole())) {
            if(!currentUser.getUserId().equals(student.getUser().getUserId())) {
                throw new UserException("Not AUTHORIZED");
            }

        }

        if(request.getUserId()!=null) {
            student.setUser(userRepository.findById(request.getUserId()).orElseThrow(() ->new  UserException("User not found")));
        }
        if(request.getPhone()!=null) {
            student.setPhone(request.getPhone());
        }
        if(request.getDepartment()!=null) {
            student.setDepartment(request.getDepartment());
        }

        if(request.getCgpa()!=null) {
            student.setCgpa(request.getCgpa());
        }
        if(request.getGraduationYear()!=null) {
            student.setGraduationYear(request.getGraduationYear());
        }
        if(request.getGender()!=null) {
            student.setGender(request.getGender());
        }
        if(request.getAddress()!=null) {
            student.setAddress(request.getAddress());
        }
        if(request.getActiveBacklogs()!=null) {
            student.setActiveBacklogs(request.getActiveBacklogs());
        }

        Student saved = studentRepository.save(student);

        return studentMapper.toDTO(saved);
    }

    @Override
    public StudentDTO updateMyProfile(CreateStudentRequest request) {

        CurrentUser currentUser = userService.getCurrentUser();

        Student student = studentRepository
                .findByUserUserId(currentUser.getUserId())
                .orElseThrow(() ->
                        new UserException("Student not found"));

        return updateProfile(student.getStudentId(), request);
    }

    @Override
    public void deleteProfile(Long studentId) {
        CurrentUser currentUser = userService.getCurrentUser();
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new UserException("Student not found"));

        if (RoleConstants.STUDENT.name().equals(currentUser.getRole())
                && !currentUser.getUserId().equals(student.getUser().getUserId())) {
            throw new UserException("NOT AUTHORIZED");
        }

        User user = student.getUser();
        user.setStatus("DELETED");
        userRepository.save(user);

    }

    @Override
    public void deleteMyProfile() {

        CurrentUser currentUser = userService.getCurrentUser();

        Student student = studentRepository
                .findByUserUserId(currentUser.getUserId())
                .orElseThrow(() ->
                        new UserException("Student not found"));

        deleteProfile(student.getStudentId());
    }

    @Override
    public void bulkDeleteStudents(List<Long> studentIds) {
        CurrentUser currentUser = userService.getCurrentUser();
        if(RoleConstants.STUDENT.name().equals(currentUser.getRole())) {
            throw new UserException("NOT AUTHORIZED");
        }
        studentRepository.deleteAllById(studentIds);
    }

    @Override
    @Transactional
    public List<StudentDTO> bulkUpdateStudents(List<BulkStudentRequest> requests) {
        CurrentUser currentUser = userService.getCurrentUser();
        if(RoleConstants.STUDENT.name().equals(currentUser.getRole())) {
            throw new UserException("NOT AUTHORIZED");
        }
        Map<Long, CreateStudentRequest> requestMap = requests.stream()
                .collect(Collectors.toMap(
                        BulkStudentRequest::getId,
                        BulkStudentRequest::getStudentData
                ));

        List<Student> students = studentRepository.findAllById(
                requests.stream()
                        .map(BulkStudentRequest::getId)
                        .toList()
        );

        for (Student student : students) {

            CreateStudentRequest request =
                    requestMap.get(student.getStudentId());
            if (request == null) {
                continue;
            }
            if (request.getPhone() != null) {
                student.setPhone(request.getPhone());
            }

            if (request.getDepartment() != null) {
                student.setDepartment(request.getDepartment());
            }

            if (request.getCgpa() != null) {
                student.setCgpa(request.getCgpa());
            }

            if (request.getGraduationYear() != null) {
                student.setGraduationYear(request.getGraduationYear());
            }

            if (request.getGender() != null) {
                student.setGender(request.getGender());
            }

            if (request.getAddress() != null) {
                student.setAddress(request.getAddress());
            }

            if (request.getActiveBacklogs() != null) {
                student.setActiveBacklogs(request.getActiveBacklogs());
            }
        }

        List<Student> updatedStudents = studentRepository.saveAll(students);

        return updatedStudents.stream()
                .map(studentMapper::toDTO)
                .toList();
    }

    public MyProfileResponse getMyProfile() {

        CurrentUser currentUser = userService.getCurrentUser();

        Student student = studentRepository
                .findByUserUserId(currentUser.getUserId())
                .orElseThrow(() -> new UserException("Student not found"));

        User user = student.getUser();

        return MyProfileResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())

                .studentId(student.getStudentId())
                .phone(student.getPhone())
                .department(student.getDepartment())
                .cgpa(student.getCgpa())
                .graduationYear(student.getGraduationYear())
                .gender(student.getGender())
                .address(student.getAddress())
                .placementStatus(student.getPlacementStatus())
                .activeBacklogs(student.getActiveBacklogs())

                .resume(resumeMapper.toDTO(student.getResume()))
                .skills(skillMapper.toDTOList(student.getSkills()))
                .applications(student.getApplications().stream().map(applicationMapper::mapToResponse).toList())

                .build();
    }

    @Override
    public ProfileCompletionResponse isProfileCompleted(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new UserException("Student not found"));

        double totalFields = 10D;
        double completedFields = 0D;

        List<String> msg = new ArrayList<>();

        if (student.getSkills() != null && !student.getSkills().isEmpty()) {
            completedFields++;
        } else {
            msg.add("Skills not found");
        }

        if (student.getResume() != null) {
            completedFields++;
        } else {
            msg.add("Resume not found");
        }

        if (student.getPhone() != null) {
            completedFields++;
        } else {
            msg.add("Phone not found");
        }

        if (student.getDepartment() != null) {
            completedFields++;
        } else {
            msg.add("Department not found");
        }

        if (student.getCgpa() != null) {
            completedFields++;
        } else {
            msg.add("CGPA not found");
        }

        if (student.getGraduationYear() != null) {
            completedFields++;
        } else {
            msg.add("Graduation Year not found");
        }

        if (student.getGender() != null) {
            completedFields++;
        } else {
            msg.add("Gender not found");
        }

        if (student.getAddress() != null) {
            completedFields++;
        } else {
            msg.add("Address not found");
        }

        if (student.getActiveBacklogs() != null) {
            completedFields++;
        } else {
            msg.add("Active Backlogs not found");
        }

        if (student.getPlacementStatus() != null) {
            completedFields++;
        } else {
            msg.add("Placement Status not found");
        }

        double percentage = (completedFields / totalFields) * 100;
        ProfileCompletionResponse profileCompletionResponse = new ProfileCompletionResponse();
        profileCompletionResponse.setCompletionPercentage(percentage);
        profileCompletionResponse.setMissingFields(msg);
        return profileCompletionResponse;
    }

    @Override
    public ProfileCompletionResponse isMyProfileCompleted() {
        CurrentUser currentUser = userService.getCurrentUser();
        Student student = studentRepository
                .findByUserUserId(currentUser.getUserId())
                .orElseThrow(() -> new UserException("Student not found, Create Student Profile"));
        return isProfileCompleted(student.getStudentId());

    }
}