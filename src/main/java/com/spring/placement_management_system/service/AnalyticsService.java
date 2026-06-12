package com.spring.placement_management_system.service;

import com.spring.placement_management_system.dto.response.DashboardAnalyticsDTO;
import com.spring.placement_management_system.dto.response.DepartmentPlacementDTO;
import com.spring.placement_management_system.dto.response.SkillResponse;
import com.spring.placement_management_system.dto.response.StudentPlacementDTO;

import java.util.List;

public interface AnalyticsService {

    DashboardAnalyticsDTO getDashboardAnalytics();

    List<DepartmentPlacementDTO> getDepartmentWisePlacement();

    List<StudentPlacementDTO> getSelectedStudents();

    List<StudentPlacementDTO> getRejectedStudents();

}
