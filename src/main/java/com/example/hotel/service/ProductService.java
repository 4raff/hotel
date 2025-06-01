package com.example.hotel.service;

import com.example.hotel.model.ProductModel;
import com.example.hotel.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductModel> getAllProducts() {
        return repository.findAll();
    }

    public ProductModel addProduct(ProductModel product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(null); // pastikan tidak terisi saat create
        return repository.save(product);
    }

    public ProductModel deleteProduct(Long id) {
        ProductModel product = repository.findById(id).orElse(null);
        if (product != null) {
            repository.delete(product);
        }
        return product;
    }

    public List<ProductModel> searchProducts(String keyword) {
        return repository.findByNamaBarangContainingIgnoreCase(keyword);
    }

}
