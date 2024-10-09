package com.dnnsvrmln.hubservice.controller;

import com.dnnsvrmln.hubservice.model.dto.OrderRequest;
import com.dnnsvrmln.hubservice.model.dto.OrderResponse;
import com.dnnsvrmln.hubservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor()
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        var order = orderService.placeOrder(orderRequest);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping("/another-round-of-beers/{id}")
    public ResponseEntity<OrderResponse> placeAnotherRoundOfBeers(@PathVariable int id) {
        var order = orderService.placeAnotherRoundOfBeer(id);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable int id) {
        var order = orderService.getOrderById(id);

        return ResponseEntity.ok(order);
    }

}
