package com.dnnsvrmln.pourservice.repository;

import com.dnnsvrmln.pourservice.model.Beer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BeerRepository {

    private final List<Beer> beers;

    private BeerRepository() {
        beers = createBeers();
    }

    public List<Beer> findAll() {
        return beers;
    }

    public Beer findById(int id) {
        return beers.stream()
                    .filter(beer -> beer.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer with ID: '" + id + "' not found"));
    }

    private static List<Beer> createBeers() {
        var beers = new ArrayList<Beer>();

        beers.add(Beer.builder().id(1).name("Pale Ale").bartenderPreparationTime(6).volume(200).pourTime(10).build());
        beers.add(Beer.builder().id(2).name("IPA").bartenderPreparationTime(7).volume(220).pourTime(11).build());
        beers.add(Beer.builder().id(3).name("Stout").bartenderPreparationTime(8).volume(300).pourTime(12).build());
        beers.add(Beer.builder().id(4).name("Lager").bartenderPreparationTime(9).volume(330).pourTime(13).build());
        beers.add(Beer.builder().id(5).name("Porter").bartenderPreparationTime(10).volume(350).pourTime(14).build());
        beers.add(Beer.builder().id(6).name("Wheat Beer").bartenderPreparationTime(11).volume(400).pourTime(15).build());
        beers.add(Beer.builder().id(7).name("Saison").bartenderPreparationTime(12).volume(450).pourTime(16).build());
        beers.add(Beer.builder().id(8).name("Pilsner").bartenderPreparationTime(13).volume(500).pourTime(17).build());
        beers.add(Beer.builder().id(9).name("Amber Ale").bartenderPreparationTime(14).volume(600).pourTime(18).build());
        beers.add(Beer.builder().id(10).name("Barleywine").bartenderPreparationTime(15).volume(630).pourTime(19).build());
        beers.add(Beer.builder().id(11).name("Blonde Ale").bartenderPreparationTime(16).volume(750).pourTime(20).build());
        beers.add(Beer.builder().id(12).name("Brown Ale").bartenderPreparationTime(17).volume(800).pourTime(21).build());
        beers.add(Beer.builder().id(13).name("Belgian Dubbel").bartenderPreparationTime(18).volume(850).pourTime(22).build());
        beers.add(Beer.builder().id(14).name("Tripel").bartenderPreparationTime(19).volume(900).pourTime(23).build());
        beers.add(Beer.builder().id(15).name("Quadrupel").bartenderPreparationTime(20).volume(1000).pourTime(24).build());

        return beers;
    }

}
