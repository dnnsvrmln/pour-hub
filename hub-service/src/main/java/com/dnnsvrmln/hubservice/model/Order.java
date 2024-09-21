package com.dnnsvrmln.hubservice.model;

import com.dnnsvrmln.hubservice.model.dto.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private List<OrderItem> orderItems;

    public static Order map(OrderRequest orderRequest) {
        return Order.builder().orderItems(orderRequest.getOrderItemDtos().stream().map(OrderItem::map).toList()).build();
    }
}
