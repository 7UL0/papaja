package com.papaja.papaja;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PapajaHelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello papaja!";
    }
}

