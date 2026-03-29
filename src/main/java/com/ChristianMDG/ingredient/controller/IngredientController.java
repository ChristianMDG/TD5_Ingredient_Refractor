package com.ChristianMDG.ingredient.controller;

import ch.qos.logback.core.model.Model;
import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.entity.StockValue;
import com.ChristianMDG.ingredient.entity.enums.UnitEnum;
import com.ChristianMDG.ingredient.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@AllArgsConstructor
public class IngredientController {
    private IngredientRepository ingredientRepository;

    @GetMapping("/ingredients")
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.getAllIngredients();
    }
    @GetMapping("/ingredient/{id}")
    public ResponseEntity<?>  getIngredientById(@PathVariable Integer id) {
        Ingredient ingredient = ingredientRepository.getIngredientById(id);
        if (ingredient == null) {
          return new ResponseEntity<>("Ingredient.id=" + id + " is not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @GetMapping("/ingredients/{id}/stock")
    public ResponseEntity<?> getStockValue(
            @PathVariable Integer id,
            @RequestParam(required = false) String at,
            @RequestParam(required = false) String unit
    ) {
        Ingredient ingredient = ingredientRepository.getIngredientById(id);
        if (at == null || unit == null) {
            return new ResponseEntity<>("Either mandatory query parameter `at` or\n" +
                    "`unit` is not provided",HttpStatus.BAD_REQUEST);
        }
        if (ingredient == null) {
            return new ResponseEntity<>("Ingredient.id=" + id + " is not found",HttpStatus.NOT_FOUND);
        }
        StockValue stockValue = ingredientRepository.getStockValueAt(Instant.parse(at),id, UnitEnum.valueOf(unit));

        return ResponseEntity.ok(stockValue);
    }
}
