package com.ecommerce.bestNutrition.controller;

import com.ecommerce.bestNutrition.exception.CartException;
import com.ecommerce.bestNutrition.exception.ProductException;
import com.ecommerce.bestNutrition.exception.UserException;
import com.ecommerce.bestNutrition.model.Cart;
import com.ecommerce.bestNutrition.model.User;
import com.ecommerce.bestNutrition.request.CartItemRequest;
import com.ecommerce.bestNutrition.response.ApiResponse;
import com.ecommerce.bestNutrition.service.CartService;
import com.ecommerce.bestNutrition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader(value = "Authorization", required = false) String jwt) throws UserException, CartException {
        User user = jwt != null ? userService.findUserProfileByJwt(jwt) : null;
        Cart cart = user != null ? cartService.findUserCart(user.getId()) : new Cart();

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody CartItemRequest cartItemRequest,
                                                     @RequestHeader(value = "Authorization", required = false) String jwt) throws UserException, ProductException {
        if (jwt == null || jwt.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse( "User not authenticated", false), HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findUserProfileByJwt(jwt);

        if (user == null) {
            return new ResponseEntity<>(new ApiResponse("User not authenticated", false), HttpStatus.UNAUTHORIZED);
        }
        cartService.addCartItem(user.getId(), cartItemRequest);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Item added to cart");
        apiResponse.setStatus(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}




/*
package com.ecommerce.bestNutrition.controller;

import com.ecommerce.bestNutrition.exception.ProductException;
import com.ecommerce.bestNutrition.exception.UserException;
import com.ecommerce.bestNutrition.model.Cart;
import com.ecommerce.bestNutrition.model.User;
import com.ecommerce.bestNutrition.request.CartItemRequest;
import com.ecommerce.bestNutrition.response.ApiResponse;
import com.ecommerce.bestNutrition.service.CartService;
import com.ecommerce.bestNutrition.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@Tag(name = "Cart Management", description = "Find user cart, add item to cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Operation(description = "find cart by user id")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return  new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody CartItemRequest cartItemRequest,
                                                     @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), cartItemRequest);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Item add to cart");
        apiResponse.setStatus(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
*/