package com.ecommerce.bestNutrition.service;

import com.ecommerce.bestNutrition.exception.CartItemException;
import com.ecommerce.bestNutrition.exception.UserException;
import com.ecommerce.bestNutrition.model.Cart;
import com.ecommerce.bestNutrition.model.CartItem;
import com.ecommerce.bestNutrition.model.Product;

import java.util.List;

public interface CartItemService {
    CartItem createCartItem(CartItem cartItem);

    CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

    CartItem isCartItemExist(Cart cart, Product product, Long userId);

    void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    CartItem findCartItemById(Long cartItemId) throws CartItemException;

    List<CartItem> findUserCartItems(Long userId);
}
