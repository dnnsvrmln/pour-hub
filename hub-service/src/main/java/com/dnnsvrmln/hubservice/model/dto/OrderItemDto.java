package com.dnnsvrmln.hubservice.model.dto;

import com.dnnsvrmln.hubservice.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private int beerId;
    private int quantity;

    public static OrderItemDto map(OrderItem orderItem) {
        return OrderItemDto.builder().beerId(orderItem.getBeerId()).quantity(orderItem.getQuantity()).build();
    }
}
