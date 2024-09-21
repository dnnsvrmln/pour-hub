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

        beers.add(Beer.builder().id(1).name("Pale Ale").bartenderPreparationTime(30).volume(330).pourTime(15).build());
        beers.add(Beer.builder().id(2).name("IPA").bartenderPreparationTime(45).volume(500).pourTime(20).build());
        beers.add(Beer.builder().id(3).name("Stout").bartenderPreparationTime(60).volume(400).pourTime(25).build());
        beers.add(Beer.builder().id(4).name("Lager").bartenderPreparationTime(25).volume(250).pourTime(10).build());
        beers.add(Beer.builder().id(5).name("Porter").bartenderPreparationTime(40).volume(350).pourTime(22).build());
        beers.add(Beer.builder().id(6).name("Wheat Beer").bartenderPreparationTime(35).volume(300).pourTime(18).build());
        beers.add(Beer.builder().id(7).name("Saison").bartenderPreparationTime(50).volume(450).pourTime(30).build());
        beers.add(Beer.builder().id(8).name("Pilsner").bartenderPreparationTime(20).volume(200).pourTime(12).build());
        beers.add(Beer.builder().id(9).name("Amber Ale").bartenderPreparationTime(55).volume(330).pourTime(28).build());
        beers.add(Beer.builder().id(10).name("Barleywine").bartenderPreparationTime(70).volume(500).pourTime(35).build());
        beers.add(Beer.builder().id(11).name("Blonde Ale").bartenderPreparationTime(45).volume(275).pourTime(14).build());
        beers.add(Beer.builder().id(12).name("Brown Ale").bartenderPreparationTime(30).volume(350).pourTime(17).build());
        beers.add(Beer.builder().id(13).name("Belgian Dubbel").bartenderPreparationTime(65).volume(400).pourTime(24).build());
        beers.add(Beer.builder().id(14).name("Tripel").bartenderPreparationTime(55).volume(500).pourTime(26).build());
        beers.add(Beer.builder().id(15).name("Kölsch").bartenderPreparationTime(40).volume(250).pourTime(20).build());

        return beers;
    }

}
