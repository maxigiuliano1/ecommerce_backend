package com.ecommerce.bestNutrition.repository;

import com.ecommerce.bestNutrition.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUserId(Long userId);
}
