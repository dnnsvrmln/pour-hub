package com.dnnsvrmln.hubservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private int id;
    private List<OrderItemDto> orderItemsDto;

    public static OrderDto map(OrderRequest orderRequest) {
        return OrderDto.builder().orderItemsDto(orderRequest.getOrderItemsDto()).build();
    }
}
