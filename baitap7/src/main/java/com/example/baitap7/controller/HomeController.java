package com.example.baitap7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.baitap7.model.Course;
import com.example.baitap7.repository.CourseRepository;

@Controller
public class HomeController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping({"/", "/home"})
    public String home(Model model,
            @RequestParam(defaultValue = "0") int page) {

        Page<Course> coursePage
                = courseRepository.findAll(PageRequest.of(page, 5));

        model.addAttribute("courses", coursePage);

        return "home";
    }

    @GetMapping("/api/search")
    @ResponseBody
    public List<Course> search(@RequestParam String keyword) {

        return courseRepository
                .findByNameContainingIgnoreCase(keyword);
    }
}
