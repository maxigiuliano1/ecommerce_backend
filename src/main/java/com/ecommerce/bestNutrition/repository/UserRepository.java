package com.ecommerce.bestNutrition.repository;

import com.ecommerce.bestNutrition.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
