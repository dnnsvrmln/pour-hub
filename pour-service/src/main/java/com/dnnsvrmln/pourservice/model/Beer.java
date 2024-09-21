package com.dnnsvrmln.pourservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beer {
    private int id;
    private String name;
    private int bartenderPreparationTime;
    private int volume;
    private int pourTime;

    @Override
    public String toString() {
        return String.format("%d,%s,%d,%d,%d%n", id, name, bartenderPreparationTime, volume, pourTime);
    }
}
