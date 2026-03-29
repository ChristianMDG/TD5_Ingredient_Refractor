package com.ChristianMDG.ingredient.controller;

import com.ChristianMDG.ingredient.entity.Dish;
import com.ChristianMDG.ingredient.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class DishController {

    private final DishRepository dishRepository;

    @GetMapping("/dishes")
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }
}