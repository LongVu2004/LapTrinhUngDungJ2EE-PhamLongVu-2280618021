package com.example.baitap7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.baitap7.model.Course;
import com.example.baitap7.repository.CourseRepository;

@Controller
@RequestMapping("/admin/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    // Hiển thị danh sách học phần
    @GetMapping
    public String listCourses(Model model) {

        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);

        return "admin/course-list";
    }

    // Hiển thị form thêm
    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("course", new Course());

        return "admin/course-create";
    }

    // Lưu học phần
    @PostMapping("/save")
    public String saveCourse(@ModelAttribute Course course) {

        courseRepository.save(course);

        return "redirect:/admin/courses";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model) {

        Course course = courseRepository.findById(id).orElse(null);

        model.addAttribute("course", course);

        return "admin/course-edit";
    }

    // Xóa học phần
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {

        courseRepository.deleteById(id);

        return "redirect:/admin/courses";
    }

}
