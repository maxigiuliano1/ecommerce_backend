package com.ecommerce.bestNutrition.controller;

import com.ecommerce.bestNutrition.exception.CartException;
import com.ecommerce.bestNutrition.exception.CartItemException;
import com.ecommerce.bestNutrition.exception.ProductException;
import com.ecommerce.bestNutrition.exception.UserException;
import com.ecommerce.bestNutrition.model.CartItem;
import com.ecommerce.bestNutrition.model.Product;
import com.ecommerce.bestNutrition.model.User;
import com.ecommerce.bestNutrition.request.CartItemRequest;
import com.ecommerce.bestNutrition.response.ApiResponse;
import com.ecommerce.bestNutrition.service.CartItemService;
import com.ecommerce.bestNutrition.service.CartService;
import com.ecommerce.bestNutrition.service.ProductService;
import com.ecommerce.bestNutrition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCartItem(@RequestBody CartItemRequest cartItemRequest,
                                                   @RequestHeader("Authorization") String jwt) throws UserException, ProductException, CartException {
        User user = userService.findUserProfileByJwt(jwt);
        Product product = productService.findById(cartItemRequest.getProductId());
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setCart(cartService.findUserCart(user.getId()));
        cartItem.setUserId(user.getId());
        cartItem.setPrice((int) (cartItemRequest.getQuantity() * product.getPrice()));
        cartItem.setDiscountedPrice(cartItemRequest.getQuantity() * product.getDiscountedPrice());

        cartItemService.createCartItem(cartItem);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Item added to cart successfully");
        apiResponse.setStatus(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable("id") Long cartItemId,
                                                      @RequestBody CartItem cartItem,
                                                      @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Item updated successfully");
        apiResponse.setStatus(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("id") Long cartItemId,
                                                      @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Item removed from cart successfully");
        apiResponse.setStatus(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getUserCartItems(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<CartItem> cartItems = cartItemService.findUserCartItems(user.getId());

        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }
}
