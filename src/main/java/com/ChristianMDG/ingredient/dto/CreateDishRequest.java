package com.ChristianMDG.ingredient.dto;

import com.ChristianMDG.ingredient.entity.enums.DishTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDishRequest {
    private String name;
    private DishTypeEnum dishType;
    private Double price;
}