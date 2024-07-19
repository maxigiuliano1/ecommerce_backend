package com.ecommerce.bestNutrition.service.impl;

import com.ecommerce.bestNutrition.model.Carousel;
import com.ecommerce.bestNutrition.repository.CarouselRepository;
import com.ecommerce.bestNutrition.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    private CarouselRepository carouselRepository;

    public CarouselServiceImpl(CarouselRepository carouselRepository){
        this.carouselRepository = carouselRepository;
    }

    @Override
    public void save(Carousel carousel) {
        carouselRepository.save(carousel);
    }

    @Override
    public Carousel findById(Long id) {
        return carouselRepository.findById(id).orElse(null);
    }

    @Override
    public List<Carousel> findAll() {
        return carouselRepository.findAll();
    }

    @Override
    public Carousel updateCarousel(Long id, Carousel carouselUpdate) throws Exception {
        Carousel carousel = findById(id);
        try{
            carousel.setName(carouselUpdate.getName());
            carousel.setDescription(carouselUpdate.getDescription());
            carousel.setImageUrl(carouselUpdate.getImageUrl());
            return carouselRepository.save(carousel);
        } catch(Exception e){
            throw new Exception("Carousel no encontrado. " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        carouselRepository.deleteById(id);
    }
}
