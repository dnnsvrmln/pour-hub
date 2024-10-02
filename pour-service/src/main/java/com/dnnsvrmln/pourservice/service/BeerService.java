package com.dnnsvrmln.pourservice.service;

import com.dnnsvrmln.pourservice.model.dto.BeerResponse;
import com.dnnsvrmln.pourservice.model.dto.PourBeerResponse;
import com.dnnsvrmln.pourservice.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public PourBeerResponse pourBeer(int id) {
        var beer = beerRepository.findById(id);

        try {
            var totalPourTime = beer.getBartenderPreparationTime() + beer.getPourTime();
            Thread.sleep(totalPourTime * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Failed to pour '%s' beer", beer.getName()));
        }

        return PourBeerResponse.map(beer, "Your beer is poured!");
    }

}
