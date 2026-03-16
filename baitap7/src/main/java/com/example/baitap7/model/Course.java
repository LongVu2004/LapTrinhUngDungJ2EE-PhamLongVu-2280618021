package com.example.baitap7.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;

    private String image;

    private int credits;

    private String lecturer;

    @ManyToOne
    @JoinColumn(name = "category_id")

    private Category category;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
}
