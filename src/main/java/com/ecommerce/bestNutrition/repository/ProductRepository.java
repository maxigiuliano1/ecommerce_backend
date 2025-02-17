package com.ecommerce.bestNutrition.repository;

import com.ecommerce.bestNutrition.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
            " WHERE p.category.name = :category OR :category = '' ")
    List<Product> filterProducts(@Param("category") String category);
}
