package com.ChristianMDG.ingredient.entity;

import com.ChristianMDG.ingredient.entity.enums.DishTypeEnum;

import java.util.List;

public class Dish {
    private Integer id;
    private String name;
    private DishTypeEnum dishType;
    private Double price;
    private List<DishIngredient> ingredients;
}
