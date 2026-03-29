package com.ChristianMDG.ingredient.controller;

import ch.qos.logback.core.model.Model;
import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.entity.StockValue;
import com.ChristianMDG.ingredient.entity.enums.UnitEnum;
import com.ChristianMDG.ingredient.repository.IngredientRepository;
import com.ChristianMDG.ingredient.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class IngredientController {
    private IngredientService ingredientService;

    @GetMapping("/ingredients")
    public ResponseEntity<?> getAllIngredients() {
        return new ResponseEntity<>(ingredientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/ingredient/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Integer id) {
        try {
            Ingredient ingredient = ingredientService.getIngredientById(id);
            return new ResponseEntity<>(ingredient, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ingredients/{id}/stock")
    public ResponseEntity<?> getStockValue(
            @PathVariable Integer id,
            @RequestParam(required = false) String at,
            @RequestParam(required = false) String unit
    ) {
        try {
            StockValue stockValue = ingredientService.getStockValue(id, at, unit);
            return ResponseEntity.ok(stockValue);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}