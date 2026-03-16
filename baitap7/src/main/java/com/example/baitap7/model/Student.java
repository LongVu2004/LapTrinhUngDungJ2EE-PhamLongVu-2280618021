package com.example.baitap7.model;

import java.util.HashSet;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "student")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "student_id")
    private Long studentId;

    private String username;

    private String password;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_role",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();
}
