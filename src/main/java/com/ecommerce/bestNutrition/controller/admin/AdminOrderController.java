package com.ecommerce.bestNutrition.controller.admin;

import com.ecommerce.bestNutrition.exception.OrderException;
import com.ecommerce.bestNutrition.model.Order;
import com.ecommerce.bestNutrition.response.ApiResponse;
import com.ecommerce.bestNutrition.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrdersHandler(){
        List<Order> res = orderService.findAll();
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> confirmedOrderHandler(@PathVariable("orderId") Long orderId,
                                                       @RequestHeader("Authorization") String jwt) throws OrderException {
        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Order> shippedOrderHandler(@PathVariable("orderId") Long orderId,
                                                     @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> deliverOrderHandler(@PathVariable("orderId") Long orderId,
                                                     @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrderHandler(@PathVariable("orderId") Long orderId,
                                                     @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.canceledOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable("orderId") Long orderId,
                                                          @RequestHeader("Authorization") String jwt) throws OrderException{
        orderService.deleteById(orderId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Order deleted successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
