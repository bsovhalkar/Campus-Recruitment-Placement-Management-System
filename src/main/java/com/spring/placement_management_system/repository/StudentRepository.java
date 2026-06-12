package com.spring.placement_management_system.repository;


import com.spring.placement_management_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    Optional<Student> findByUserUserId(Long userId);
    Student getById(Long id);
//    Long count();
}