package com.dnnsvrmln.hubservice.repository;

import com.dnnsvrmln.hubservice.model.Order;
import com.dnnsvrmln.hubservice.model.OrderItem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class OrderRepository {

    private final Map<Integer, Order> orderMap = new HashMap<>();
    private final AtomicInteger orderIdCounter = new AtomicInteger(1);

    public Order place(List<OrderItem> orderItems) {
        var orderId = orderIdCounter.getAndIncrement();
        var order = new Order(orderId, orderItems);
        orderMap.put(orderId, order);

        return order;
    }

    public Order findOrderById(int id) {
        return Optional.ofNullable(orderMap.get(id))
                       .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with ID: '" + id + "' not found"));
    }

}
