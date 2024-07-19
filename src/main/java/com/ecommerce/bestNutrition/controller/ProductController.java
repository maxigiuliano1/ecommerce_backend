package com.ecommerce.bestNutrition.controller;

import com.ecommerce.bestNutrition.exception.ProductException;
import com.ecommerce.bestNutrition.model.Product;
import com.ecommerce.bestNutrition.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger LOGGER = LogManager.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/{category}")
    public ResponseEntity<List<Product>> findProductByCategoryHandler(@PathVariable("category") String category){
        List<Product> res = productService.findProductByCategory(category);

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        List<Product> res = productService.findAll();
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Long id) throws ProductException {
        Product res = productService.findById(id);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
}
