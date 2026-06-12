package com.spring.placement_management_system.controller.admin;


import com.spring.placement_management_system.dto.CompanyDTO;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/companies")
@RequiredArgsConstructor
public class AdminCompanyController {

    private final CompanyService companyService;

    /**
     * Create Company
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createCompany(
            @RequestBody CompanyDTO companyDTO) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Company created successfully",
                        companyService.createCompany(companyDTO)
                )
        );
    }

    /**
     * Get All Companies
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCompanies() {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Companies fetched successfully",
                        companyService.getAllCompanies()
                )
        );
    }

    /**
     * Get Company By Id
     */
    @GetMapping("/{companyId}")
    public ResponseEntity<ApiResponse> getCompanyById(
            @PathVariable Long companyId) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Company fetched successfully",
                        companyService.getCompanyById(companyId)
                )
        );
    }

    /**
     * Update Company
     */
    @PutMapping("/{companyId}")
    public ResponseEntity<ApiResponse> updateCompany(
            @PathVariable Long companyId,
            @RequestBody CompanyDTO companyDTO) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Company updated successfully",
                        companyService.updateCompany(
                                companyId,
                                companyDTO
                        )
                )
        );
    }

    /**
     * Delete Company
     */
    @DeleteMapping("/{companyId}")
    public ResponseEntity<ApiResponse> deleteCompany(
            @PathVariable Long companyId) {

        companyService.deleteCompany(companyId);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Company deleted successfully"
                )
        );
    }
}