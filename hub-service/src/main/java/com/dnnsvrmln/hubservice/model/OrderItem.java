package com.dnnsvrmln.hubservice.model;

import com.dnnsvrmln.hubservice.model.dto.PourResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class OrderItem {
    private int beerId;
    private String beerName;
    private int quantity;

    private void incrementQuantity() {
        this.quantity++;
    }

    public static List<OrderItem> aggregateOrderItems(List<PourResponse> pourResponses) {
        var orderItemMap = new HashMap<Integer, OrderItem>();

        pourResponses.forEach(pourResponse -> {
            int beerId = pourResponse.getId();
            if (orderItemMap.containsKey(beerId)) {
                var existingOrderItem = orderItemMap.get(beerId);
                existingOrderItem.incrementQuantity();
            } else {
                OrderItem newOrderItem = OrderItem.builder().beerId(pourResponse.getId()).beerName(pourResponse.getName()).quantity(1).build();
                orderItemMap.put(beerId, newOrderItem);
            }
        });

        return new ArrayList<>(orderItemMap.values());
    }
}
