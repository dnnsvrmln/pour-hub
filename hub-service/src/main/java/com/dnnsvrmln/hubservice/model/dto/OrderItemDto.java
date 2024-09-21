package com.dnnsvrmln.hubservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private int beerId;
    private String beerName;
    private int quantity;
}
