package com.ChristianMDG.ingredient.controller;

import com.ChristianMDG.ingredient.entity.Dish;
import com.ChristianMDG.ingredient.repository.DishRepository;
import com.ChristianMDG.ingredient.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping("/dishes")
    public ResponseEntity<?> getAllDishes() {
        return new ResponseEntity<>(dishService.getAllDishes(), HttpStatus.OK);
    }
}