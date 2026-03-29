package com.ChristianMDG.ingredient.entity;

import com.ChristianMDG.ingredient.entity.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Ingredient {
    private Integer id;
    private String name;
    private Double price;
    private CategoryEnum category;
   // private List<StockMovement> stockMovementList;
}
