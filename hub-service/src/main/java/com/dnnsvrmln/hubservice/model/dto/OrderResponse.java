package com.dnnsvrmln.hubservice.model.dto;

import com.dnnsvrmln.hubservice.model.Order;
import com.dnnsvrmln.hubservice.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private int id;
    private List<OrderItem> orderItems;

    public static OrderResponse map(Order order) {
        return OrderResponse.builder().id(order.getId()).orderItems(order.getOrderItems()).build();
    }
}
