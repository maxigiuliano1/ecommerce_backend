package com.ecommerce.bestNutrition.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "categorias")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotNull
    private String name;

    public Category(){}

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
