package com.dnnsvrmln.hubservice.model;

import com.dnnsvrmln.hubservice.model.dto.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class OrderItem {
    private int beerId;
    private String beerName;
    private int quantity;

    public static OrderItem map(OrderItemDto orderItemDto) {
        return OrderItem.builder().beerId(orderItemDto.getBeerId()).beerName(orderItemDto.getBeerName()).quantity(orderItemDto.getQuantity()).build();
    }
}
