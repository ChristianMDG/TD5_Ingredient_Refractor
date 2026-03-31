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

    public List<Ingredient> findIngredients(int size, int offset) {
        ingredientValidator.validatePagination(size, offset);
        return ingredientRepository.findIngredients(size, offset);
    }

    public List<Ingredient> searchIngredients(
            String ingredientName,
            CategoryEnum category,
            String dishName,
            int page,
            int size
    ) {
        ingredientValidator.validatePagination(size, page);
        return ingredientRepository.findIngredientsByCriteria(
                ingredientName, category, dishName, page, size
        );
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.getAllIngredients();
    }

    public Ingredient getIngredientById(Integer id) {
        ingredientValidator.validateId(id);

        Ingredient ingredient = ingredientRepository.getIngredientById(id);

        if (ingredient == null) {
            throw new RuntimeException("Ingredient with id=" + id + " not found");
        }

        return ingredient;
    }

    public StockValue getStockValue(Integer id, String at, String unit) {
        ingredientValidator.validateStockParams(id, at, unit);

        Instant instant = Instant.parse(at);
        UnitEnum unitEnum = UnitEnum.valueOf(unit.toUpperCase());

        Ingredient ingredient = ingredientRepository.getIngredientById(id);

        if (ingredient == null) {
            throw new RuntimeException("Ingredient with id=" + id + " not found");
        }

        return ingredientRepository.getStockValueAt(instant, id, unitEnum);
    }
}
