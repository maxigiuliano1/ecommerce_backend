package com.ecommerce.bestNutrition.repository;

import com.ecommerce.bestNutrition.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
