package com.api.estacionamento;

import com.api.estacionamento.controllers.EstacionamentoController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@Slf4j
public class EstacionamentoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EstacionamentoApplication.class, args);

        EstacionamentoController estacionamentoController;

        System.out.println("\n \n Hello Parking!");
    }

    @GetMapping("/hello")
    public String index() {
        log.info("Request Get para Hello");
        return "Olá! Bem vindo ao nosso estacionamento!";

    }

}
