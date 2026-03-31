package com.ChristianMDG.ingredient.service;

import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.entity.StockValue;
import com.ChristianMDG.ingredient.entity.enums.CategoryEnum;
import com.ChristianMDG.ingredient.entity.enums.UnitEnum;
import com.ChristianMDG.ingredient.repository.IngredientRepository;
import com.ChristianMDG.ingredient.validator.IngredientValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor

public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientValidator ingredientValidator;

    public List<Ingredient> findIngredients(int page, int size) {
        ingredientValidator.validatePagination(page, size);
        return ingredientRepository.findIngredients(page, size);
    }

    public List<Ingredient> searchIngredients(
            String name,
            CategoryEnum category,
            String dishName,
            int page,
            int size
    ) {
        ingredientValidator.validatePagination(page, size);

        return ingredientRepository.findIngredientsByCriteria(
                name, category, dishName, page, size
        );
    }
public List<Ingredient> findAll(){
        return ingredientRepository.getAllIngredients();
}

    public Ingredient getIngredientById(Integer id) {
        ingredientValidator.validateId(id);

        Ingredient ingredient = ingredientRepository.getIngredientById(id);

        if (ingredient == null|| ingredient.getId()==null) {
            throw new RuntimeException("Ingredient.id=" + id + " not found");
        }

        return ingredient;
    }

    public StockValue getStockValue(Integer id, String at, String unit) {
        ingredientValidator.validateStockParams(id, at, unit);

        Instant instant;
        UnitEnum unitEnum;

        try {
            instant = Instant.parse(at);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format (use ISO-8601)");
        }

        try {
            unitEnum = UnitEnum.valueOf(unit.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid unit");
        }

        getIngredientById(id);
        return ingredientRepository.getStockValueAt(instant, id, unitEnum);
    }
}