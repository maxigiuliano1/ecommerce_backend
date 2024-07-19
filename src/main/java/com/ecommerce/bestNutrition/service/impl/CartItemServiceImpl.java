package com.ecommerce.bestNutrition.service.impl;

import com.ecommerce.bestNutrition.exception.CartItemException;
import com.ecommerce.bestNutrition.exception.UserException;
import com.ecommerce.bestNutrition.model.Cart;
import com.ecommerce.bestNutrition.model.CartItem;
import com.ecommerce.bestNutrition.model.Product;
import com.ecommerce.bestNutrition.model.User;
import com.ecommerce.bestNutrition.repository.CartItemRepository;
import com.ecommerce.bestNutrition.repository.CartRepository;
import com.ecommerce.bestNutrition.service.CartItemService;
import com.ecommerce.bestNutrition.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository){
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice((int) (cartItem.getProduct().getPrice() * cartItem.getQuantity()));
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());

        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice((int) (item.getProduct().getPrice() * item.getQuantity()));
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, Long userId) {
        return cartItemRepository.isCartItemExist(cart, product, userId);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);
        User user = userService.findUserById(cartItem.getUserId());

        User userReq = userService.findUserById(userId);

        if(user.getId().equals(userReq.getId())){
            cartItemRepository.deleteById(cartItemId);
        } else {
            throw new UserException("You can't remove another users item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if(cartItem.isPresent()){
            return cartItem.get();
        }
        throw new CartItemException("CartItem not found with id: " + cartItemId);
    }

    @Override
    public List<CartItem> findUserCartItems(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        return cart.getCartItems().stream().toList();
    }
}
