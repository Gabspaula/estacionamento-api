package com.api.estacionamento.dtos;

import com.api.estacionamento.models.TamanhoVeiculo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistroEstacionamentoDTO {

    @NotBlank
    private String placaVeiculo;

    private Double valorTotal;

    private TamanhoVeiculo tamanhoVeiculo;

//    public void calcularValorTotal() {
//        this.valorTotal = calcularValorTotal();
//    }
}
