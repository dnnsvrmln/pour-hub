package com.dnnsvrmln.pourservice.model.dto;

import com.dnnsvrmln.pourservice.model.Beer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerResponse {
    private int id;
    private String name;
    private int bartenderPreparationTime;
    private int volume;
    private int pourTime;

    public static BeerResponse map(Beer beer) {
        return BeerResponse.builder()
                           .id(beer.getId())
                           .name(beer.getName())
                           .bartenderPreparationTime(beer.getBartenderPreparationTime())
                           .volume(beer.getVolume())
                           .pourTime(beer.getPourTime())
                           .build();
    }
}
