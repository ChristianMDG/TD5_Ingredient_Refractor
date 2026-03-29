package com.ChristianMDG.ingredient.service;

import com.ChristianMDG.ingredient.entity.Dish;
import com.ChristianMDG.ingredient.repository.DishRepository;
import com.ChristianMDG.ingredient.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    public List<Dish> getAllDishes()
    {
        return dishRepository.findAll();
    }

}
