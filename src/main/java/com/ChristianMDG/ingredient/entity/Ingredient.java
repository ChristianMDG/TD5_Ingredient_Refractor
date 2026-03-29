package com.ChristianMDG.ingredient.entity;

import com.ChristianMDG.ingredient.entity.enums.CategoryEnum;

import java.util.List;

public class Ingredient {
    private Integer id;
    private String name;
    private Double price;
    private CategoryEnum category;
    private List<StockMovement> stockMovementList;
}
