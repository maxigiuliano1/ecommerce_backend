package com.ecommerce.bestNutrition.controller.admin;

import com.ecommerce.bestNutrition.model.Carousel;
import com.ecommerce.bestNutrition.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/carousel")
public class AdminCarouselController {
    @Autowired
    private CarouselService carouselService;

    @GetMapping("/{id}")
    public ResponseEntity<Carousel> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(carouselService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Carousel> save(@RequestBody Carousel carousel){
        if(carousel != null){
            carouselService.save(carousel);
            return ResponseEntity.status(HttpStatus.OK).body(carousel);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(carousel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Carousel carousel) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(carouselService.updateCarousel(id,carousel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        carouselService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Carousel borrado con exito.");
    }
}
