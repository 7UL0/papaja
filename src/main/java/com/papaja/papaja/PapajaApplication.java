package com.papaja.papaja;

import com.papaja.papaja.model.Fruit;
import com.papaja.papaja.repository.FruitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PapajaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PapajaApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FruitRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Fruit(null, "Apple",  "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQt0Q2DFO56Vh4bMGDeeMLlT8KZE7oeis5NTr9ZRfTUG8yG08pDo6iqQVYbUaQNQNHQC3uAfmISYwr6rAw9u6LDeg"));
                repo.save(new Fruit(null, "Banana", "https://example.com/images/banana.jpg"));
                repo.save(new Fruit(null, "Cherry", "https://example.com/images/cherry.jpg"));
                repo.save(new Fruit(null, "Date",   "https://example.com/images/date.jpg"));
                repo.save(new Fruit(null, "Fig",    "https://example.com/images/fig.jpg"));
                repo.save(new Fruit(null, "Grape",  "https://example.com/images/grape.jpg"));
                repo.save(new Fruit(null, "Kiwi",   "https://example.com/images/kiwi.jpg"));
                repo.save(new Fruit(null, "Lemon",  "https://example.com/images/lemon.jpg"));
                repo.save(new Fruit(null, "Mango",  "https://example.com/images/mango.jpg"));
                repo.save(new Fruit(null, "Orange", "https://example.com/images/orange.jpg"));
            }
        };
    }
}
