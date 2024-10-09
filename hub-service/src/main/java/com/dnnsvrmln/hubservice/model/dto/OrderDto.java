package com.dnnsvrmln.hubservice.model.dto;

import com.dnnsvrmln.hubservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private int id;
    private List<OrderItemDto> orderItemsDto;

    public static OrderDto map(OrderRequest orderRequest) {
        return OrderDto.builder().orderItemsDto(orderRequest.getOrderItemsDto()).build();
    }

    public static OrderDto map(Order order) {
        return OrderDto.builder().orderItemsDto(order.getOrderItems().stream().map(OrderItemDto::map).toList()).build();
    }
}
