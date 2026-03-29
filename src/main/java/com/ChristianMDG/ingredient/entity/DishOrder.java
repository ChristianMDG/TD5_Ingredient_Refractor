package com.ChristianMDG.ingredient.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DishOrder {
    private Integer id;
    private Dish dish;
    private Integer quantity;
}
