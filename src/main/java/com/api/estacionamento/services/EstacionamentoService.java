package com.api.estacionamento.services;

import com.api.estacionamento.models.RegistroEstacionamento;
import com.api.estacionamento.repositories.EstacionamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EstacionamentoService {

    final EstacionamentoRepository estacionamentoRepository;
    public boolean ultimoRegistro;

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

    public Optional<RegistroEstacionamento> findByPlacaVeiculo(String placaVeiculo) {
        return estacionamentoRepository.findByPlacaVeiculo(placaVeiculo);
    }

    @Transactional
    public ResponseEntity<String> deleteById(UUID id) {
        var registroEstacionamento = estacionamentoRepository.findById(id);
        if (registroEstacionamento.isPresent()) {
            try {
                estacionamentoRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("Veículo deletado com sucesso.");
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao deletar registro.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não encontrado.");
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
        var registroEstacionamento = estacionamentoRepository.findByPlacaVeiculo(placaVeiculo);
        if (!registroEstacionamento.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var updateSaida = registroEstacionamento.get();
        updateSaida.setSaida(LocalDateTime.now(ZoneId.of("UTC")));
        updateSaida.setValorTotal(5.00);
        updateSaida.setUltimoRegistro(1);
        return ResponseEntity.status(HttpStatus.OK).body(estacionamentoRepository.save(updateSaida));
    }
}