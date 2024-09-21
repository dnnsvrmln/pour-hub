package com.dnnsvrmln.pourservice.service;

import com.dnnsvrmln.pourservice.model.dto.BeerResponse;
import com.dnnsvrmln.pourservice.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BeerService {

    private final BeerRepository beerRepository;

    public List<BeerResponse> getAllBeers() {
        var beers = beerRepository.findAll();

        return beers.stream().map(BeerResponse::map).toList();
    }

    public BeerResponse getBeerById(int id) {
        var beer = beerRepository.findById(id);

        return BeerResponse.map(beer);
    }

}
