package com.example.bai4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.bai4.model.Category;
import com.example.bai4.model.Product;
import com.example.bai4.service.CategoryService;
import com.example.bai4.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // ================= LIST =================
    @GetMapping
    public String index(Model model) {
        model.addAttribute("listProduct", productService.getAll());
        return "product/products";
    }

    // ================= CREATE FORM =================
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    // ================= CREATE SUBMIT =================
    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        // gán category
        Category category = categoryService.findById(categoryId);
        product.setCategory(category);

        // upload image
        if (!imageProduct.isEmpty()) {
            productService.updateImage(product, imageProduct);
        }

        productService.add(product);
        return "redirect:/products";
    }

    // ================= EDIT FORM =================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {

        Product product = productService.get(id);
        if (product == null) {
            return "redirect:/products";
        }

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    // ================= EDIT SUBMIT =================
    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable int id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        // gán lại id
        product.setId(id);

        // gán category
        Category category = categoryService.findById(categoryId);
        product.setCategory(category);

        // nếu có ảnh mới thì update
        if (!imageProduct.isEmpty()) {
            productService.updateImage(product, imageProduct);
        }

        productService.update(product);
        return "redirect:/products";
    }

    // ================= DELETE =================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.getAll().removeIf(p -> p.getId() == id);
        return "redirect:/products";
    }
}
