package com.example.baitap7.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.baitap7.model.Enrollment;
import com.example.baitap7.model.Student;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudent(Student student);

}
