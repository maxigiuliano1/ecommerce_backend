package com.ecommerce.bestNutrition.service.impl;

import com.ecommerce.bestNutrition.exception.CartException;
import com.ecommerce.bestNutrition.exception.ProductException;
import com.ecommerce.bestNutrition.exception.UserException;
import com.ecommerce.bestNutrition.model.Cart;
import com.ecommerce.bestNutrition.model.CartItem;
import com.ecommerce.bestNutrition.model.Product;
import com.ecommerce.bestNutrition.model.User;
import com.ecommerce.bestNutrition.repository.CartRepository;
import com.ecommerce.bestNutrition.request.CartItemRequest;
import com.ecommerce.bestNutrition.service.CartItemService;
import com.ecommerce.bestNutrition.service.CartService;
import com.ecommerce.bestNutrition.service.ProductService;
import com.ecommerce.bestNutrition.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;
    private UserService userService;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService,
                           ProductService productService, UserService userService){
        this.cartItemService = cartItemService;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, CartItemRequest req) throws ProductException, UserException {
        // Aseguramos que el usuario esté guardado
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new UserException("User not found with id: " + userId);
        }

        // Buscamos el carrito del usuario
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = createCart(user);
        }

        Product product = productService.findById(req.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(cart, product, userId);

        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setCart(cart);
            cartItem.setUserId(userId);

            int price = req.getQuantity() * product.getDiscountedPrice();
            cartItem.setPrice(price);

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Item Add To Cart";
    }

    @Override
    public Cart findUserCart(Long userId) throws CartException {
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            throw new CartException("Cart not found with userId: " + userId);
        }

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for(CartItem cartItem : cart.getCartItems()){
            totalPrice = totalPrice + cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
            totalItem = totalItem + cartItem.getQuantity();
        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscounted(totalPrice - totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}
