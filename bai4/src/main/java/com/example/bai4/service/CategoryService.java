package com.example.bai4.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bai4.model.Category;

@Service
public class CategoryService {

    List<Category> listCategory = new ArrayList<>();
    
    public CategoryService() {
        listCategory.add(new Category(1,"Điện thoại"));
        listCategory.add(new Category(2, "Laptop"));
        listCategory.add(new Category(3, "Phụ kiện"));
        listCategory.add(new Category(4, "Gia dụng"));
    }

    public List<Category> getAll() {
        return listCategory;
    }

    public Category findById(int id) {
        return listCategory.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
