package com.api.estacionamento.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_estacionamento")
@Data
public class RegistroEstacionamento implements Serializable {

    @Id
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

    @Column(nullable = true, length = 15)
    private TamanhoVeiculo tamanhoVeiculo;
}
