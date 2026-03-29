package com.ChristianMDG.ingredient.entity;

import com.ChristianMDG.ingredient.entity.enums.MovementTypeEnum;

import java.time.Instant;

public class StockMovement {
    private Integer id;
    private StockValue value;
    private MovementTypeEnum type;
    private Instant creationDateTime;
}
