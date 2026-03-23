package com.example.demoproduct.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demoproduct.model.Product;
import com.example.demoproduct.repository.CategoryRepository;
import com.example.demoproduct.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findPaginated(int pageNo, int pageSize, String keyword, String sortField, String sortDir, Long categoryId) {
        // Thiết lập hướng sắp xếp (Tăng dần hoặc Giảm dần)
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        // Tạo Pageable bao gồm: trang hiện tại, kích thước, và bộ lọc sắp xếp
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId, pageable);
        }

        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }
        return productRepository.findAll(pageable);
    }
}
