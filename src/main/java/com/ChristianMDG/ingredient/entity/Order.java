package com.ChristianMDG.ingredient.entity;

import com.ChristianMDG.ingredient.entity.enums.OrderStatusEnum;
import com.ChristianMDG.ingredient.entity.enums.OrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private Integer id;
    private String reference;
    private Instant creationDateTime;
    private List<DishOrder> dishOrders;
    private OrderTypeEnum type;
    private OrderStatusEnum status;
}
