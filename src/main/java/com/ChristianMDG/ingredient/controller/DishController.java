package com.ChristianMDG.ingredient.controller;

import com.ChristianMDG.ingredient.entity.Dish;
import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.service.DishService;
import com.ChristianMDG.ingredient.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class DishController {

    private final DishService dishService;
    private final IngredientService ingredientService;
    @GetMapping("/dishes")
    public ResponseEntity<?> getAllDishes() {
        return new ResponseEntity<>(dishService.getAllDishes(), HttpStatus.OK);
    }

    @PutMapping("/dishes/{id}/ingredients")
    public ResponseEntity<?> updateDishIngredients(
            @PathVariable Integer id,
            @RequestBody(required = false) List<Ingredient> ingredients) {

        if (ingredients == null || ingredients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The request body must contain a list of ingredients to associate or dissociate.");
        }
        List<Ingredient> ingredientsList = ingredientService.findAll();
        Set<Integer> validIds = ingredientsList.stream()
                .map(Ingredient::getId)
                .collect(Collectors.toSet());

        List<Ingredient> validIngredients = ingredients.stream()
                .filter(ingredient -> validIds.contains(ingredient.getId()))
                .toList();

        try {
           Dish updatedIngredientIds = dishService.updateDishIngredients(id, validIngredients);
            return ResponseEntity.ok(updatedIngredientIds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Dish "+id+" is not found");
        }
    }
}