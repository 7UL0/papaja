package com.papaja.papaja;

import com.papaja.papaja.model.Fruit;
import com.papaja.papaja.service.FruitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruits")
public class FruitController {
    private final FruitService service;

    public FruitController(FruitService service) {
        this.service = service;
    }

    @GetMapping
    public List<Fruit> all() {
        return service.findAll();
    }

    @PostMapping
    public Fruit create(@RequestBody Fruit fruit) {
        return service.save(fruit);
    }
}
