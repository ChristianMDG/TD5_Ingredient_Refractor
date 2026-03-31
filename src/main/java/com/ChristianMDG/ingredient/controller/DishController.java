package com.ChristianMDG.ingredient.controller;


import com.ChristianMDG.ingredient.dto.CreateDishRequest;
import com.ChristianMDG.ingredient.entity.Dish;
import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.service.DishService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;

  /*  @GetMapping
    public ResponseEntity<?> getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }*/

    @GetMapping("/searchDish")
    public ResponseEntity<?> getDishesByIngredientName(@RequestParam String ingredientName) {
        return ResponseEntity.ok(dishService.findDishByIngredientName(ingredientName));
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateDishIngredients(
            @PathVariable Integer id,
            @RequestBody List<Ingredient> ingredients
    ) {
        return ResponseEntity.ok(
                dishService.updateDishIngredients(id, ingredients)
        );
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDishes(@RequestBody List<CreateDishRequest> requests) {

        try {
            List<Dish> dishes = dishService.createDishes(requests);
            return ResponseEntity.status(HttpStatus.CREATED).body(dishes);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getDishes(
            @RequestParam(required = false) Double priceUnder,
            @RequestParam(required = false) Double priceOver,
            @RequestParam(required = false) String name) {

        List<Dish> dishes = dishService.getDishesFiltered(priceUnder, priceOver, name);
        return ResponseEntity.ok(dishes);
    }
}