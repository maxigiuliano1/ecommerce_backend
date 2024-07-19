package com.ecommerce.bestNutrition.controller.admin;

import com.ecommerce.bestNutrition.exception.ProductException;
import com.ecommerce.bestNutrition.model.Product;
import com.ecommerce.bestNutrition.request.ProductRequest;
import com.ecommerce.bestNutrition.response.ApiResponse;
import com.ecommerce.bestNutrition.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest req){
        Product product = productService.save(req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId) throws ProductException{
        productService.deleteById(productId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Product deleted Successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> products = productService.findAll();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest productReq,
                                                 @PathVariable("productId") Long productId) throws ProductException{
        Product product = productService.updateProduct(productId, productReq);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
}
