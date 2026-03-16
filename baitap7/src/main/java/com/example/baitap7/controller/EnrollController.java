package com.example.baitap7.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.baitap7.model.Course;
import com.example.baitap7.model.Enrollment;
import com.example.baitap7.model.Student;
import com.example.baitap7.repository.CourseRepository;
import com.example.baitap7.repository.EnrollmentRepository;
import com.example.baitap7.repository.StudentRepository;

@Controller
@RequestMapping("/enroll")
public class EnrollController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @PostMapping("/{courseId}")
    public String enrollCourse(@PathVariable Long courseId, Principal principal) {

        String username = principal.getName();

        Student student = studentRepository.findByUsername(username);

        Course course = courseRepository.findById(courseId).orElse(null);

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);

        return "redirect:/home";
    }
}
