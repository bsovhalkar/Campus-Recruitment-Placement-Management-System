package com.spring.placement_management_system.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ProfileCompletionResponse {
    private double completionPercentage;
    private List<String> missingFields;
}