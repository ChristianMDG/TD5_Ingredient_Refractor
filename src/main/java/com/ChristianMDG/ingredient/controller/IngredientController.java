package com.ChristianMDG.ingredient.controller;

import ch.qos.logback.core.model.Model;
import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class IngredientController {
    private IngredientRepository ingredientRepository;

    @GetMapping("/ingredients")
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.getAllIngredients();
    }
}
