package com.example.baitap7.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.baitap7.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
