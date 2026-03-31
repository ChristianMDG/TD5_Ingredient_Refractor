package com.ChristianMDG.ingredient.controller;

import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.entity.StockValue;
import com.ChristianMDG.ingredient.entity.enums.CategoryEnum;
import com.ChristianMDG.ingredient.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
@GetMapping
public ResponseEntity<?> findAll(){
    return  ResponseEntity.ok(ingredientService.findAll());
}

    @GetMapping("/paginated")
    public ResponseEntity<?> findIngredients(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(
                ingredientService.findIngredients(page, size)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Ingredient>> searchIngredients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) CategoryEnum category,
            @RequestParam(required = false) String dishName,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        return ResponseEntity.ok(
                ingredientService.searchIngredients(name, category, dishName, page, size)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ingredientService.getIngredientById(id)
        );
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<StockValue> getStock(
            @PathVariable Integer id,
            @RequestParam String at,
            @RequestParam String unit
    ) {
        return ResponseEntity.ok(
                ingredientService.getStockValue(id, at, unit)
        );
    }
}