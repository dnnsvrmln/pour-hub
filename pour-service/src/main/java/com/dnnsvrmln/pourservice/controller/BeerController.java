package com.dnnsvrmln.pourservice.controller;

import com.dnnsvrmln.pourservice.model.dto.BeerResponse;
import com.dnnsvrmln.pourservice.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/api/beer", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;

    @GetMapping
    public ResponseEntity<List<BeerResponse>> getAllBeers() {
        var beers = beerService.getAllBeers();

        return ResponseEntity.ok(beers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeerResponse> getBeerById(@PathVariable int id) {
        var beer = beerService.getBeerById(id);

        return ResponseEntity.ok(beer);
    }

}
