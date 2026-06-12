package com.spring.placement_management_system.controller;

import com.spring.placement_management_system.dto.SkillDTO;
import com.spring.placement_management_system.dto.request.SkillRequest;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    /**
     * Add Skills
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addSkill(
            @Valid @RequestBody SkillRequest request) {

        List<SkillDTO> skills =
                skillService.addSkill(request);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Skills added successfully",
                        skills
                )
        );
    }

    /**
     * Get My Skills
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMySkills() {

        List<SkillDTO> skills =
                skillService.getSkills();

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Skills fetched successfully",
                        skills
                )
        );
    }

    /**
     * Update Skill
     */
    @PutMapping("/{skillId}")
    public ResponseEntity<ApiResponse> updateSkill(
            @PathVariable Long skillId,
            @Valid @RequestBody SkillRequest request) {

        String skills =
                skillService.updateSkill(skillId, request);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Skill updated successfully",
                        skills
                )
        );
    }

    /**
     * Delete Skill
     */
    @DeleteMapping("/{skillId}")
    public ResponseEntity<ApiResponse> deleteSkill(
            @PathVariable Long skillId) {

        skillService.deleteSkill(skillId);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Skill deleted successfully"
                )
        );
    }

    @GetMapping("{skillId}")
    public ResponseEntity<ApiResponse> getSkillById(
            @PathVariable Long skillId
    ){
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Skill fetched successfully",
                        skillService.getSkillById(skillId)
                )
        );
    }

    /**
     * Check Skill Completion
     */
    @GetMapping("/status")
    public ResponseEntity<ApiResponse> skillStatus() {

        String hasSkills =
                skillService.getStatus();

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        hasSkills,
                        "Responded"
                )
        );
    }
}