package com.spring.placement_management_system.dto.mapper;

import com.spring.placement_management_system.dto.SkillDTO;
import com.spring.placement_management_system.dto.StudentDTO;
import com.spring.placement_management_system.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Component
public class StudentMapper {
    private final SkillMapper skillMapper;
    private final ResumeMapper resumeMapper;
    private final UserMapper userMapper;
    public StudentDTO toDTO(Student student) {

        if (student == null) {
            return null;
        }

        StudentDTO dto = new StudentDTO();

        dto.setStudentId(student.getStudentId());

        dto.setUser(
                userMapper.toDTO(student.getUser())
        );

        dto.setResume(
                resumeMapper.toDTO(student.getResume())
        );
        if(student.getSkills() != null) {

            List<SkillDTO> skills =
                    student.getSkills()
                            .stream()
                            .map(skillMapper::toDTO)
                            .collect(Collectors.toList());

            dto.setSkills(skills);
        }

        dto.setPhone(student.getPhone());
        dto.setDepartment(student.getDepartment());
        dto.setCgpa(student.getCgpa());
        dto.setGraduationYear(student.getGraduationYear());
        dto.setGender(student.getGender());
        dto.setAddress(student.getAddress());
        dto.setActiveBacklogs(student.getActiveBacklogs());
        dto.setPlacementStatus(student.getPlacementStatus());

        return dto;
    }
}