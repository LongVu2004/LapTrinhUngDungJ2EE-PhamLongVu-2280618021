package com.example.baitap7.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.baitap7.model.Enrollment;
import com.example.baitap7.model.Student;
import com.example.baitap7.repository.EnrollmentRepository;
import com.example.baitap7.repository.StudentRepository;

@Controller
public class MyCourseController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/my-courses")
    public String myCourses(Model model, Principal principal) {

        String username = principal.getName();

        Student student = studentRepository.findByUsername(username);

        List<Enrollment> enrollments
                = enrollmentRepository.findByStudent(student);

        model.addAttribute("enrollments", enrollments);

        return "my-courses";
    }

}
