package com.api.estacionamento.controllers;

import com.api.estacionamento.dtos.RegistroEstacionamentoDTO;
import com.api.estacionamento.models.RegistroEstacionamento;
import com.api.estacionamento.services.EstacionamentoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping(value = "/estacionamento")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstacionamentoController {

    @Autowired
    private final EstacionamentoService estacionamentoService;

    public EstacionamentoController(EstacionamentoService estacionamentoService) {
        this.estacionamentoService = estacionamentoService;
    }

    // TODO validar o ultimo registro, se o veiculo esta no estacionamento ou nao
    @PostMapping(value = "/registrar")
    public ResponseEntity<Object> saveRegistroVeiculo(@RequestBody @Valid RegistroEstacionamentoDTO registroEstacionamentoDTO) {
        if (registroEstacionamentoDTO.getPlacaVeiculo().length() != 7) {
            log.info("A placa do carro deve ter exatamente 7 caracteres.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A placa do veículo deve ter exatamente 7 caracteres.");
        }
        log.info("Verificando se veículo já deu entrada.");
        if (estacionamentoService.existsByPlaca(registroEstacionamentoDTO.getPlacaVeiculo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Veículo já deu entrada.");
        }
        log.info("Salvando registro no banco de dados.");
        var registroEstacionamento = new RegistroEstacionamento();
        BeanUtils.copyProperties(registroEstacionamentoDTO, registroEstacionamento);
        registroEstacionamento.setEntrada(LocalDateTime.now(ZoneId.of("UTC")));
        registroEstacionamento.setUltimoRegistro(0);
        return ResponseEntity.status(HttpStatus.CREATED).body(estacionamentoService.save(registroEstacionamento));
    }

    // TODO adicionar validação de se estacionamento vazio log message
    @GetMapping(value = "/all")
    public ResponseEntity<List<RegistroEstacionamento>> findAll() {
        log.info("Buscando todos os registros.");
        List<RegistroEstacionamento> result = estacionamentoService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{placaVeiculo}")
    public ResponseEntity<RegistroEstacionamento> procuraRegistroPlaca(@PathVariable String placaVeiculo) {
        log.info("Buscando registro por placa");
        return estacionamentoService.findByPlacaVeiculo(placaVeiculo)
                .map(registro -> ResponseEntity.status(HttpStatus.OK).body(registro))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
        log.info("Deletando registro por id");
        return estacionamentoService.deleteById(id);
    }

    // TODO adicionar validação de veiculo por ultimoRegistro
    @PutMapping("/{placaVeiculo}")
    public ResponseEntity<RegistroEstacionamento> updateRegistro(@PathVariable String placaVeiculo) {
        log.info("Registro de saída de veículo.");
        return estacionamentoService.updateRegistro(placaVeiculo);
    }
}