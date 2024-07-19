package com.ecommerce.bestNutrition.service.impl;

import com.ecommerce.bestNutrition.exception.CartException;
import com.ecommerce.bestNutrition.exception.OrderException;
import com.ecommerce.bestNutrition.model.*;
import com.ecommerce.bestNutrition.repository.CartRepository;
import com.ecommerce.bestNutrition.repository.OrderRepository;
import com.ecommerce.bestNutrition.service.CartService;
import com.ecommerce.bestNutrition.service.OrderService;
import com.ecommerce.bestNutrition.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private CartService cartService;
    private CartRepository cartRepository;
    private ProductService productService;
    private OrderRepository orderRepository;

    public OrderServiceImpl(CartService cartItemService, CartRepository cartRepository,
                            ProductService productService, OrderRepository orderRepository){
        this.cartService = cartItemService;
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(User user, Address shippingAddress) throws OrderException, CartException {
        Cart cart = cartService.findUserCart(user.getId());

        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new OrderException("Cart is empty or not found for user: " + user.getUsername());
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PLACED");
        order.setTotalAmount(cart.getTotalPrice());
        order.setTotalItem(cart.getTotalItem());
        order.setTotalDiscontinuedAmount(cart.getTotalDiscountedPrice());
        order.setDiscounted(cart.getDiscounted());

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());
            orderItem.setUserId(user.getId());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long id) throws OrderException {
        return orderRepository.findById(id).orElseThrow(() -> new OrderException("Order not found with id: " + id));    }

    @Override
    public List<Order> userOrderHistory(Long id) {
        return orderRepository.findByUserId(id);
    }

    @Override
    public Order placedOrder(Long id) throws OrderException {
        Order order = findById(id);
        order.setOrderStatus("PLACED");
        return orderRepository.save(order);
    }

    @Override
    public Order confirmedOrder(Long id) throws OrderException {
        Order order = findById(id);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long id) throws OrderException {
        Order order = findById(id);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long id) throws OrderException {
        Order order = findById(id);
        order.setOrderStatus("DELIVERED");
        order.setDeliveryDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(Long id) throws OrderException {
        Order order = findById(id);
        order.setOrderStatus("CANCELED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(Long id) throws OrderException {
        Order order = findById(id);
        orderRepository.delete(order);
    }
}
