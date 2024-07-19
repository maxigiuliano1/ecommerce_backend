package com.ecommerce.bestNutrition.controller;

import com.ecommerce.bestNutrition.model.Carousel;
import com.ecommerce.bestNutrition.service.CarouselService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

    private static final Logger LOGGER = LogManager.getLogger(CarouselController.class);

    @GetMapping
    public ResponseEntity<List<Carousel>> findAll(){
        return ResponseEntity.ok(carouselService.findAll());
    }

}
