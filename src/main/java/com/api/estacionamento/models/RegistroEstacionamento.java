package com.api.estacionamento.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_estacionamento")
@Data
public class RegistroEstacionamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 7)
    private String placaVeiculo;

    @Column(nullable = true, unique = true, length = 10)
    private LocalDateTime entrada;

    @Column(nullable = true, unique = true, length = 10)
    private LocalDateTime saida;

    @Column(nullable = true, unique = true, length = 10)
    private Double valorTotal;

    @Column(nullable = true, length = 10)
    private int ultimoRegistro;

//    @Column(nullable = true, length = 15)
//    private TamanhoVeiculo tamanhoVeiculo;
}
