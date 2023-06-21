package com.api.estacionamento.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagasEstacionamento {

    private int vagas;
    private boolean isEmpty;
    private RegistroEstacionamento registroEstacionamento;

    public void vagaOcupada(RegistroEstacionamento registroEstacionamento) {
        this.registroEstacionamento = registroEstacionamento;
        this.isEmpty = false;
    }

    public void vagaVazia() {
        registroEstacionamento = null;
        this.isEmpty = true;
    }

}
