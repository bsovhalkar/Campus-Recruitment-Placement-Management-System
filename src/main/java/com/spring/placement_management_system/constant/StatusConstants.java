package com.spring.placement_management_system.constant;

import java.util.Arrays;
import java.util.Optional;

public enum StatusConstants {
    APPLIED,

    UNDER_REVIEW,

    TEST_SCHEDULED,
    SHORTLISTED_FOR_TEST,
    SHORTLISTED,
    SHORTLISTED_FOR_INTERVIEW,
    INTERVIEW_SCHEDULED ,
    SELECTED,

    REJECTED,

    WITHDRAWN,

    NOT_APPLIED;


    public static Optional<StatusConstants> from(String value) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(value))
                .findFirst();
    }
}

