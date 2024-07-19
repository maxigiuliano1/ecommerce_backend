package com.ecommerce.bestNutrition.service;

import com.ecommerce.bestNutrition.exception.ProductException;
import com.ecommerce.bestNutrition.model.Product;
import com.ecommerce.bestNutrition.request.ProductRequest;

import java.util.List;

public interface ProductService {
    Product save(ProductRequest req);

    String deleteById(Long productId) throws ProductException; //puede ser un void

    Product updateProduct(Long productId, ProductRequest productRequest) throws ProductException;

    Product findById(Long productId) throws ProductException;

    List<Product> findAll(); //aca puede ser un Page para paginar

    List<Product> findProductByCategory(String category);
}
