package com.spring.placement_management_system.service.impl;

import com.spring.placement_management_system.constant.RoleConstants;
import com.spring.placement_management_system.dto.CurrentUser;
import com.spring.placement_management_system.dto.SkillDTO;
import com.spring.placement_management_system.dto.mapper.SkillMapper;
import com.spring.placement_management_system.dto.request.SkillRequest;
import com.spring.placement_management_system.entity.Skill;
import com.spring.placement_management_system.entity.Student;
import com.spring.placement_management_system.entity.User;
import com.spring.placement_management_system.exception.SkillException;
import com.spring.placement_management_system.exception.UserException;
import com.spring.placement_management_system.repository.SkillRepository;
import com.spring.placement_management_system.repository.StudentRepository;
import com.spring.placement_management_system.service.SkillService;
import com.spring.placement_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl
        implements SkillService {

    private final SkillRepository skillRepository;
    private final StudentRepository studentRepository;
    private final SkillMapper skillMapper;

    private final UserService  userService;
    @Override
    public List<SkillDTO> addSkill(SkillRequest request) {

        CurrentUser currentUser = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(currentUser.getUserId()).orElseThrow(() -> new UserException("Student not found"));
        Skill skill = Skill.builder()
                .student(student)
                .skillName(request.getSkillName())
                .proficiencyLevel(
                        request.getProficiencyLevel())
                .build();

        skillRepository.save(skill);
        List<Skill> students =  skillRepository.findByStudentStudentId(student.getStudentId());
        return students.stream()
                .map(skillMapper::toDTO)
                .toList();
    }


    @Override
    public List<SkillDTO> getSkills() {

        CurrentUser currentUser = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(currentUser.getUserId()).orElseThrow(() -> new RuntimeException("Student not found"));
        List<Skill> tmp =  skillRepository.findByStudentStudentId(student.getStudentId());
        return tmp.stream()
                .map(skillMapper::toDTO)
                .toList();
    }

    @Override
    public String updateSkill(Long skillId,
                             SkillRequest request) {

        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new SkillException("Skill not found"));

        CurrentUser currentUser = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(currentUser.getUserId()).orElseThrow(() -> new RuntimeException("Student not found"));
        if(skillRepository.existsBySkillIdAndStudentStudentId(skillId, student.getStudentId())){

            skill.setSkillName(request.getSkillName());
            skill.setProficiencyLevel(
                    request.getProficiencyLevel());

            skillRepository.save(skill);
            List<Skill> students =  skillRepository.findByStudentStudentId(skillId);

            return "Skill updated successfully";
        }
        return "Something went wrong";
    }

    @Override
    public String deleteSkill(Long skillId) {

        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));

        CurrentUser currentUser = userService.getCurrentUser();
        if(RoleConstants.STUDENT.name().equals(currentUser.getRole())){
            Student student = studentRepository.findById(skill.getStudent().getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student having this skill not found"));

            if (!currentUser.getUserId().equals(student.getUser().getUserId())) {
                throw new RuntimeException("NOT AUTHORIZED");
            }
        }

        skillRepository.deleteById(skillId);
        return "Skill deleted successfully";
    }
    @Override
    public String getStatus(){
        CurrentUser currentUser = userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(currentUser.getUserId()).orElseThrow(() -> new RuntimeException("Student not found"));
        if(skillRepository.existsByStudentStudentId(student.getStudentId())){
            return "Skills added";
        }
        return "No skills added";
    }
    @Override
    public SkillDTO getSkillById(Long skillId) {
        CurrentUser currentUser = userService.getCurrentUser();
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new SkillException("Skill not found"));
        Student student = studentRepository.findByUserUserId(currentUser.getUserId()).orElseThrow(() -> new RuntimeException("Student not found"));
        if(!skill.getStudent().getStudentId().equals(student.getStudentId())){
            throw new SkillException("NOT AUTHORIZED");
        }
        return skillMapper.toDTO(skill);
    }

}