package com.dnnsvrmln.hubservice.service;

import com.dnnsvrmln.hubservice.model.Order;
import com.dnnsvrmln.hubservice.model.dto.OrderRequest;
import com.dnnsvrmln.hubservice.model.dto.OrderResponse;
import com.dnnsvrmln.hubservice.repository.OrderRepository;
import com.dnnsvrmln.pourservice.model.Beer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class OrderService {

    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        var orderItems = Order.map(orderRequest).getOrderItems();

        for (var orderItem : orderItems) {
            try {
                var beerId = orderItem.getBeerId();
                var beer = restTemplate.getForObject("http://localhost:8081/v1/api/beer/" + beerId, Beer.class);

                orderItem.setBeerId(beerId);
                orderItem.setBeerName(beer.getName());
            } catch (HttpClientErrorException.NotFound e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer with ID: '" + orderItem.getBeerId() + "' not found");
            }
        }

        var order = orderRepository.place(orderItems);

        return OrderResponse.map(order);
    }

    public OrderResponse getOrderById(int orderId) {
        var order = orderRepository.findOrderById(orderId);

        return OrderResponse.map(order);
    }

}
