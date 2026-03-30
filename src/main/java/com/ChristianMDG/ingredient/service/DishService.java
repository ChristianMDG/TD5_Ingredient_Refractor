package com.ChristianMDG.ingredient.service;

import com.ChristianMDG.ingredient.entity.Dish;
import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.repository.DishRepository;
import com.ChristianMDG.ingredient.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    public List<Dish> getAllDishes()
    {
        return dishRepository.findAll();
    }

    public Dish updateDishIngredients(Integer idDish, List<Ingredient> ingredients){

        dishRepository.findDishById(idDish);
        return dishRepository.updateDishIngredients(idDish, ingredients);
    }
}
