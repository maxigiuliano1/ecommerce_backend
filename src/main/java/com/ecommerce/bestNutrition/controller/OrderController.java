package com.ecommerce.bestNutrition.controller;

import com.ecommerce.bestNutrition.exception.CartException;
import com.ecommerce.bestNutrition.exception.OrderException;
import com.ecommerce.bestNutrition.exception.UserException;
import com.ecommerce.bestNutrition.model.Address;
import com.ecommerce.bestNutrition.model.Order;
import com.ecommerce.bestNutrition.model.User;
import com.ecommerce.bestNutrition.service.OrderService;
import com.ecommerce.bestNutrition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createdOrder(@RequestBody Address shippingAddress,
                                              @RequestHeader("Authorization") String jwt) throws UserException, OrderException, CartException {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.save(user, shippingAddress);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);

        List<Order> orders = orderService.userOrderHistory(user.getId());

        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId,
                                               @RequestHeader("Authorization") String jwt) throws UserException, OrderException{
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findById(orderId);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }
}
