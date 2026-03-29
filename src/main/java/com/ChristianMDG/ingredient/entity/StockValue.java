package com.ChristianMDG.ingredient.entity;

import com.ChristianMDG.ingredient.entity.enums.UnitEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockValue {
    private Double quantity;
    private UnitEnum unit;
}
