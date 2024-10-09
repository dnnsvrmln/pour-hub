package com.dnnsvrmln.hubservice.service;

import com.dnnsvrmln.hubservice.configuration.BartenderConfiguration;
import com.dnnsvrmln.hubservice.model.OrderItem;
import com.dnnsvrmln.hubservice.model.dto.OrderDto;
import com.dnnsvrmln.hubservice.model.dto.OrderItemDto;
import com.dnnsvrmln.hubservice.model.dto.OrderRequest;
import com.dnnsvrmln.hubservice.model.dto.OrderResponse;
import com.dnnsvrmln.hubservice.model.dto.PourResponse;
import com.dnnsvrmln.hubservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderService {

    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;
    private final ExecutorService executorService;

    @Autowired
    public OrderService(BartenderConfiguration bartenderConfiguration, RestTemplate restTemplate, OrderRepository orderRepository) {
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
        this.executorService = Executors.newFixedThreadPool(bartenderConfiguration.getBartendersOnDuty());
    }

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        var orderItems = OrderDto.map(orderRequest).getOrderItemsDto();
        return processOrder(orderItems);
    }

    public OrderResponse placeAnotherRoundOfBeer(int previousOrderId) {
        var previousOrder = orderRepository.findOrderById(previousOrderId);
        var orderItems = OrderDto.map(previousOrder).getOrderItemsDto();

        return processOrder(orderItems);
    }

    public OrderResponse getOrderById(int orderId) {
        var order = orderRepository.findOrderById(orderId);

        return OrderResponse.map(order);
    }

    private OrderResponse processOrder(List<OrderItemDto> orderItemsDto) {
        var pourFutures = createPourFutures(orderItemsDto);

        try {
            var pourResponses = pourFutures.stream().map(CompletableFuture::join).toList();
            var order = orderRepository.place(OrderItem.aggregateOrderItems(pourResponses));
            return OrderResponse.map(order);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to process the order");
        }
    }

    private List<CompletableFuture<PourResponse>> createPourFutures(List<OrderItemDto> orderItemsDto) {
        var pourFutures = new ArrayList<CompletableFuture<PourResponse>>();

        for (var orderItem : orderItemsDto) {
            if (orderItem.getQuantity() < 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                  "For beer with ID: '" + orderItem.getBeerId() + "' the quantity must be at least one or higher");
            }

            for (int i = 0; i < orderItem.getQuantity(); i++) {
                pourFutures.add(pourBeer(orderItem.getBeerId()));
            }
        }

        return pourFutures;
    }

    private CompletableFuture<PourResponse> pourBeer(int beerId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                var requestUrl = "http://pour-service/v1/api/beer/pour?id=" + beerId;
                return restTemplate.postForObject(requestUrl, null, PourResponse.class);
            } catch (HttpClientErrorException.NotFound e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer with ID: '" + beerId + "' not found");
            }
        }, executorService);
    }

}
