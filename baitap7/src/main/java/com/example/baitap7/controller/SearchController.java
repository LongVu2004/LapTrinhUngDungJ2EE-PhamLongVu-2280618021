package com.example.baitap7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.baitap7.model.Course;
import com.example.baitap7.repository.CourseRepository;

@Controller
public class SearchController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/search")
    public String searchCourse(@RequestParam("keyword") String keyword,
            Model model) {

        List<Course> courses
                = courseRepository.findByNameContainingIgnoreCase(keyword);

        model.addAttribute("courses", courses);

        return "search-result";
    }

}
