package com.ecommerce.bestNutrition.service;

import com.ecommerce.bestNutrition.exception.CartException;
import com.ecommerce.bestNutrition.exception.OrderException;
import com.ecommerce.bestNutrition.model.Address;
import com.ecommerce.bestNutrition.model.Order;
import com.ecommerce.bestNutrition.model.User;

import java.util.List;

public interface OrderService {
    Order save(User user, Address shippingAddress) throws OrderException, CartException;

    Order findById(Long orderId) throws OrderException;

    List<Order> userOrderHistory(Long orderId);

    Order placedOrder(Long orderId) throws OrderException;

    Order confirmedOrder(Long orderId) throws OrderException;

    Order shippedOrder(Long orderId) throws OrderException;

    Order deliveredOrder(Long orderId) throws OrderException;

    Order canceledOrder(Long orderId) throws OrderException;

    List<Order> findAll();

    void deleteById(Long orderId) throws OrderException;
}
