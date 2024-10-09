package com.dnnsvrmln.pourservice.model.dto;

import com.dnnsvrmln.pourservice.model.Beer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PourBeerResponse {
    private int id;
    private String name;
    private String message;

    public static PourBeerResponse map(Beer beer, String message) {
        return PourBeerResponse.builder().id(beer.getId()).name(beer.getName()).message(message).build();
    }
}
