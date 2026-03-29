package com.ChristianMDG.ingredient.service;

import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public List<Ingredient> findAll() {
        return ingredientRepository.getAllIngredients();
    }
}
