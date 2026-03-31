package com.ChristianMDG.ingredient.controller;

import com.ChristianMDG.ingredient.entity.Dish;
import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.service.DishService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@AllArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping("/dishes")
    public ResponseEntity<List<Dish>> getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @PutMapping("/dishes/{id}/ingredients")
    public ResponseEntity<Dish> updateDishIngredients(
            @PathVariable Integer id,
            @RequestBody List<Ingredient> ingredients
    ) {
        return ResponseEntity.ok(
                dishService.updateDishIngredients(id, ingredients)
        );
    }
}