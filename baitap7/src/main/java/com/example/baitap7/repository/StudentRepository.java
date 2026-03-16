package com.example.baitap7.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.baitap7.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByUsername(String username);

}
