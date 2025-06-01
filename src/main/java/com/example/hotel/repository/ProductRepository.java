package com.example.hotel.repository;

import com.example.hotel.model.ProductModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findByNamaBarangContainingIgnoreCase(String keyword);
}
