package com.papaja.papaja.service;

import com.papaja.papaja.model.Fruit;
import com.papaja.papaja.repository.FruitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitService {
    private final FruitRepository repo;

    public FruitService(FruitRepository repo) {
        this.repo = repo;
    }

    public List<Fruit> findAll() {
        return repo.findAll();
    }

    public Fruit save(Fruit fruit) {
        return repo.save(fruit);
    }
}
