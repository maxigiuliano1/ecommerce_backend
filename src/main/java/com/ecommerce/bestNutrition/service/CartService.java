package com.ecommerce.bestNutrition.service;

import com.ecommerce.bestNutrition.exception.CartException;
import com.ecommerce.bestNutrition.exception.ProductException;
import com.ecommerce.bestNutrition.exception.UserException;
import com.ecommerce.bestNutrition.model.Cart;
import com.ecommerce.bestNutrition.model.User;
import com.ecommerce.bestNutrition.request.CartItemRequest;

public interface CartService {
    Cart createCart(User user);
    String addCartItem(Long userId, CartItemRequest req) throws ProductException, UserException;
    Cart findUserCart(Long userId) throws CartException;
}
