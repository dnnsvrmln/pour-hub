package com.dnnsvrmln.hubservice.service;

import com.dnnsvrmln.hubservice.configuration.BartenderConfiguration;
import com.dnnsvrmln.hubservice.model.Order;
import com.dnnsvrmln.hubservice.model.OrderItem;
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
        var orderItems = Order.map(orderRequest).getOrderItems();
        var futures = new ArrayList<CompletableFuture<Void>>();

        for (var orderItem : orderItems) {
            for (int i = 0; i < orderItem.getQuantity(); i++) {
                futures.add(pourBeer(orderItem));
            }
        }

        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to process the order");
        }

        var order = orderRepository.place(orderItems);
        return OrderResponse.map(order);
    }

    public OrderResponse getOrderById(int orderId) {
        var order = orderRepository.findOrderById(orderId);

        return OrderResponse.map(order);
    }

    private CompletableFuture<Void> pourBeer(OrderItem orderItem) {
        return CompletableFuture.runAsync(() -> {
            try {
                var beerId = orderItem.getBeerId();
                var beer = restTemplate.getForObject("http://pour-service/v1/api/beer/" + beerId, PourResponse.class);

                orderItem.setBeerId(beerId);
                orderItem.setBeerName(beer.getName());
                var totalPourTime = beer.getBartenderPreparationTime() + beer.getPourTime();
                Thread.sleep(totalPourTime * 1000L);
            } catch (HttpClientErrorException.NotFound e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer with ID: '" + orderItem.getBeerId() + "' not found");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to pour the beers");
            }
        }, executorService);
    }

}
