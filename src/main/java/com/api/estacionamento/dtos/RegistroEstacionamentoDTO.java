package com.api.estacionamento.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistroEstacionamentoDTO {

    @NotBlank
    private String placaVeiculo;

    private Double valorTotal;

    private int ultimoRegistro;

}
