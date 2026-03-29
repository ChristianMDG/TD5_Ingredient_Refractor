package com.ChristianMDG.ingredient.entity;

import com.ChristianMDG.ingredient.entity.enums.OrderStatusEnum;
import com.ChristianMDG.ingredient.entity.enums.OrderTypeEnum;

import java.time.Instant;
import java.util.List;

public class Order {
    private Integer id;
    private String reference;
    private Instant creationDateTime;
    private List<DishOrder> dishOrders;
    private OrderTypeEnum type;
    private OrderStatusEnum status;
}
