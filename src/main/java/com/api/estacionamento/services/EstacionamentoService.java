package com.api.estacionamento.services;

import com.api.estacionamento.models.RegistroEstacionamento;
import com.api.estacionamento.repositories.EstacionamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class EstacionamentoService {

    final EstacionamentoRepository estacionamentoRepository;

    public EstacionamentoService(EstacionamentoRepository estacionamentoRepository) {
        this.estacionamentoRepository = estacionamentoRepository;
    }

    @Transactional
    public RegistroEstacionamento save(RegistroEstacionamento registroEstacionamento) {
        return estacionamentoRepository.save(registroEstacionamento);
    }

    public List<RegistroEstacionamento> findAll() {
        return estacionamentoRepository.findAll();
    }

    public boolean existsByPlaca(String placaVeiculo) {
        return estacionamentoRepository.existsByPlacaVeiculo(placaVeiculo);
    }

    public ResponseEntity<RegistroEstacionamento> findByPlaca(String placaVeiculo) {
        var registroEstacionamento = estacionamentoRepository.findById(placaVeiculo);
        if (registroEstacionamento.isPresent()) {
            return ResponseEntity.ok(registroEstacionamento.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Transactional
    public ResponseEntity<String> deleteByPlaca(String placaVeiculo) {
        var registroEstacionamento = estacionamentoRepository.findById(placaVeiculo);
        if (registroEstacionamento.isPresent()) {
            try {
                estacionamentoRepository.deleteById(placaVeiculo);
                return ResponseEntity.status(HttpStatus.OK).body("Veículo deletado com sucesso.");
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao deletar registro.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não encontrado.");
    }
    public ResponseEntity<RegistroEstacionamento> updateRegistro(String placaVeiculo) {
        var registroEstacionamento = estacionamentoRepository.findById(placaVeiculo);
        if (!registroEstacionamento.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var updateSaida = registroEstacionamento.get();
        updateSaida.setSaida(LocalDateTime.now(ZoneId.of("UTC")));
        updateSaida.setValorTotal(5.00);
        return ResponseEntity.status(HttpStatus.OK).body(estacionamentoRepository.save(updateSaida));
    }
}