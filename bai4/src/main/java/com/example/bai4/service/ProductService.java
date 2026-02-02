package com.example.bai4.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.bai4.model.Product;

@Service
public class ProductService {
    private List<Product> listProduct = new ArrayList<>();

    // ================= GET ALL =================
    public List<Product> getAll() {
        System.out.println("GET ALL = " + listProduct.size());
        return listProduct;
    }

    // ================= GET BY ID =================
    public Product get(int id) {
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ================= ADD =================
    public void add(Product newProduct) {
        int maxId = listProduct.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);

        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
        System.out.println("TOTAL PRODUCT = " + listProduct.size());
    }

    // ================= UPDATE =================
    public void update(Product editProduct) {
        Product find = get(editProduct.getId());

        if (find != null) {
            find.setName(editProduct.getName());
            find.setPrice(editProduct.getPrice());

            if (editProduct.getImage() != null) {
                find.setImage(editProduct.getImage());
            }

            find.setCategory(editProduct.getCategory());
        }
    }

    // ================= UPDATE IMAGE =================
    public void updateImage(Product product, MultipartFile imageProduct) {

        String contentType = imageProduct.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
        }

        if (!imageProduct.isEmpty()) {
            try {
                Path dirImages = Paths.get("src/main/resources/static/images");

                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }

                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path filePath = dirImages.resolve(newFileName);

                Files.copy(imageProduct.getInputStream(),filePath,StandardCopyOption.REPLACE_EXISTING);

                product.setImage(newFileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
