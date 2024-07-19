package com.ecommerce.bestNutrition.service;


import com.ecommerce.bestNutrition.model.Carousel;

import java.util.List;

public interface CarouselService {
    void save(Carousel carousel);
    Carousel findById(Long id);
    List<Carousel> findAll();
    Carousel updateCarousel(Long id, Carousel carousel) throws Exception;
    void deleteById(Long id);
}
