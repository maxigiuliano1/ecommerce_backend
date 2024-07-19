package com.ecommerce.bestNutrition.service.impl;

import com.ecommerce.bestNutrition.exception.ProductException;
import com.ecommerce.bestNutrition.model.Category;
import com.ecommerce.bestNutrition.model.Product;
import com.ecommerce.bestNutrition.repository.CategoryRepository;
import com.ecommerce.bestNutrition.repository.ProductRepository;
import com.ecommerce.bestNutrition.request.ProductRequest;
import com.ecommerce.bestNutrition.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product save(ProductRequest req) {
        Category category = categoryRepository.findByName(req.getCategoryName());

        if(category == null){
            Category newCategory = new Category();
            newCategory.setName(req.getCategoryName());

            categoryRepository.save(newCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setAvailable(req.getAvailable());
        product.setDescription(req.getDescription());
        product.setImageUrl(req.getImageUrl());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        System.out.println("product: " + product);
        
        return savedProduct;
    }

    @Override
    public String deleteById(Long id) throws ProductException {
        Product product = findById(id);
        productRepository.delete(product);
        return "Product delete Successfully";
    }

    @Override
    public Product updateProduct(Long id, ProductRequest productRequest) throws ProductException {
        Product product = findById(id);
        try{
            if(product != null && productRequest != null){
                Category category = categoryRepository.findByName(productRequest.getCategoryName());

                if(category != null) {
                    product.setCategory(category);
                } else {
                    Category newCategory = new Category();
                    newCategory.setName(productRequest.getCategoryName());
                    categoryRepository.save(newCategory);
                    product.setCategory(newCategory);
                }

                product.setTitle(productRequest.getTitle());
                product.setQuantity(productRequest.getQuantity());
                product.setDescription(productRequest.getDescription());
                product.setImageUrl(productRequest.getImageUrl());
                product.setPrice(productRequest.getPrice());
                product.setAvailable(productRequest.getAvailable());

                return productRepository.save(product);
            } else if(productRequest != null) {
                throw new ProductException("product by id is null.");
            } else {
                throw new ProductException("product by id or productRequest is null.");
            }
        } catch(ProductException e){
            throw new ProductException("Function UpdateProduct not found." + e.getCause());
        }
    }

    @Override
    public Product findById(Long id) throws ProductException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            return productOptional.get();
        }
        throw new ProductException("Product not found with id - " + id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return productRepository.filterProducts(category);
    }
}
