package com.example.baitap7.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.baitap7.model.Role;
import com.example.baitap7.model.Student;
import com.example.baitap7.repository.RoleRepository;
import com.example.baitap7.repository.StudentRepository;

@Controller
public class AuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Trang đăng ký
    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("student", new Student());

        return "register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String register(@ModelAttribute Student student) {

        Role role = roleRepository.findByName("STUDENT");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        student.setRoles(roles);

        student.setPassword(passwordEncoder.encode(student.getPassword()));

        studentRepository.save(student);

        return "redirect:/login";
    }

    // Trang đăng nhập
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
