package com.spring.placement_management_system.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class BulkStudentRequest {

    private Long id;

    private CreateStudentRequest studentData;
}