package com.spring.placement_management_system.service;

import com.spring.placement_management_system.dto.StudentDTO;
import com.spring.placement_management_system.dto.request.BulkStudentRequest;
import com.spring.placement_management_system.dto.request.CreateStudentRequest;
import com.spring.placement_management_system.dto.response.MyProfileResponse;
import com.spring.placement_management_system.dto.response.ProfileCompletionResponse;

import java.util.List;
import java.util.Map;

public interface StudentService {

    StudentDTO createProfile(CreateStudentRequest request);

    StudentDTO getProfile(Long studentId);

    List<StudentDTO> getAllStudents();

    StudentDTO updateProfile(Long studentId,
                          CreateStudentRequest request);
    StudentDTO updateMyProfile(CreateStudentRequest request);
    void deleteProfile(Long studentId);

    void deleteMyProfile();
    void bulkDeleteStudents(List<Long> studentIds);

    public MyProfileResponse getMyProfile();
    ProfileCompletionResponse isProfileCompleted(Long studentId);
    ProfileCompletionResponse isMyProfileCompleted();
    List<StudentDTO> bulkUpdateStudents(
            List<BulkStudentRequest> requests);
    Boolean doIHaveStudentProfile();
}