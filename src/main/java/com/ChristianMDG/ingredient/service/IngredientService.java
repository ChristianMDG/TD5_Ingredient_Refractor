package com.ChristianMDG.ingredient.service;

import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.entity.StockValue;
import com.ChristianMDG.ingredient.entity.enums.CategoryEnum;
import com.ChristianMDG.ingredient.entity.enums.UnitEnum;
import com.ChristianMDG.ingredient.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public List<Ingredient> fingIngredients(Integer size, Integer offset) {
        if (size == null || size <= 0) {
            throw new IllegalArgumentException("Size cannot be null or less than 1");
        }
        if (offset == null) {
            throw new IllegalArgumentException("Offset cannot be null");
        }
       return ingredientRepository.findIngredients(size, offset);
    }

    public List<Ingredient> searchIngredients(
            String ingredientName,
            CategoryEnum category,
            String dishName,
            int page,
            int size
    ){
        return ingredientRepository.findIngredientsByCriteria(ingredientName, category, dishName, page, size);
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.getAllIngredients();
    }

    public Ingredient getIngredientById(Integer id) {
        Ingredient ingredient = ingredientRepository.getIngredientById(id);

        if (ingredient.getId() == null) {
            throw new RuntimeException("Ingredient.id=" + id + " is not found");
        }

        return ingredient;
    }
    public StockValue getStockValue(Integer id, String at, String unit) {
        if (at == null || unit == null) {
            throw new IllegalArgumentException("Either mandatory query parameter `at` or `unit` is not provided");
        }
        Ingredient ingredient = ingredientRepository.getIngredientById(id);
        if (ingredient.getId() == null) {
            throw new RuntimeException("Ingredient.id=" + id + " is not found");
        }

        Instant instant = Instant.parse(at);
        UnitEnum unitEnum = UnitEnum.valueOf(unit);
        return ingredientRepository.getStockValueAt(instant, id, unitEnum);
    }
}
