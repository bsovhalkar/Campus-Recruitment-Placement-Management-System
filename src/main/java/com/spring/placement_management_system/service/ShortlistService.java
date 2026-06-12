package com.spring.placement_management_system.service;


import com.spring.placement_management_system.dto.response.ShortlistingDTO;

import java.util.List;

public interface ShortlistService {

    List<ShortlistingDTO> generateShortlist(
            Long jobId
    );

    List<ShortlistingDTO> getShortlistedStudents(
            Long jobId
    );

}