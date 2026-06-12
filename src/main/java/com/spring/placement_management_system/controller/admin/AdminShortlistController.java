package com.spring.placement_management_system.controller.admin;

import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.dto.response.ShortlistingDTO;
import com.spring.placement_management_system.service.ShortlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/shortlist")
@RequiredArgsConstructor
public class AdminShortlistController {

    private final ShortlistService shortlistingService;

    @PostMapping("/generate/{jobId}")
    public ResponseEntity<ApiResponse> generateShortlist(
            @PathVariable Long jobId
    ) {

        List<ShortlistingDTO> response =
                shortlistingService.generateShortlist(jobId);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Shortlist generated successfully",
                        response
                )
        );
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<ApiResponse> getShortlistedStudents(
            @PathVariable Long jobId
    ) {

        List<ShortlistingDTO> response =
                shortlistingService.getShortlistedStudents(jobId);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Shortlisted students fetched successfully",
                        response
                )
        );
    }
}