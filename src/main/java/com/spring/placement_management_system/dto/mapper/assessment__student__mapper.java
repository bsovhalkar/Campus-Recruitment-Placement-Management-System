package com.spring.placement_management_system.dto.mapper;

import com.spring.placement_management_system.dto.response.AssessmentStudentDTO;
import com.spring.placement_management_system.entity.AssessmentStudent;
import com.spring.placement_management_system.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class assessment__student__mapper {
    public AssessmentStudentDTO toDTO(AssessmentStudent data) {
        return AssessmentStudentDTO.builder()
                .studentId(data.getStudent().getStudentId())
                .studentName(data.getStudent().getUser().getName())
                .score(data.getScore())
                .build();
    }
}
