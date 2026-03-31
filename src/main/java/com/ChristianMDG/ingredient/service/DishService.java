package com.ChristianMDG.ingredient.service;

import com.ChristianMDG.ingredient.entity.Dish;
import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.repository.DishRepository;
import com.ChristianMDG.ingredient.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Dish updateDishIngredients(Integer idDish, List<Ingredient> ingredients) {

        if (ingredients == null || ingredients.isEmpty()) {
            throw new IllegalArgumentException(
                    "The request body must contain a list of ingredients"
            );
        }
        Dish dish = dishRepository.findDishById(idDish);
        if (dish == null) {
            throw new RuntimeException("Dish " + idDish + " not found");
        }

        List<Ingredient> allIngredients = ingredientRepository.getAllIngredients();

        Set<Integer> validIds = allIngredients.stream()
                .map(Ingredient::getId)
                .collect(Collectors.toSet());

        List<Ingredient> validIngredients = ingredients.stream()
                .filter(i -> i.getId() != null && validIds.contains(i.getId()))
                .toList();

        if (validIngredients.isEmpty()) {
            throw new IllegalArgumentException("No valid ingredients provided");
        }

        return dishRepository.updateDishIngredients(idDish, validIngredients);
    }
}