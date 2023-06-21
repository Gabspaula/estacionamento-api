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
        return ResponseEntity.status(HttpStatus.CREATED).body(estacionamentoService.save(registroEstacionamento));
    }

    // TODO adicionar validação de se estacionamento vazio log message
    @GetMapping
    public ResponseEntity<List<RegistroEstacionamento>> findAll() {
        log.info("Buscando todos os registros.");
        List<RegistroEstacionamento> result = estacionamentoService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{placaVeiculo}")
    public ResponseEntity<RegistroEstacionamento> getPlaca(@PathVariable String placaVeiculo) {
        log.info("Buscando registro");
        return estacionamentoService.findByPlaca(placaVeiculo);
    }

    @DeleteMapping("/{placaVeiculo}")
    public ResponseEntity<String> deletePlaca(@PathVariable String placaVeiculo) {
        log.info("Deletando registro por placa");
        return estacionamentoService.deleteByPlaca(placaVeiculo);
    }

    @PutMapping("/{placaVeiculo}")
    public ResponseEntity<RegistroEstacionamento> updateRegistro(@PathVariable String placaVeiculo) {
        log.info("Registro de saída de veículo.");
        return estacionamentoService.updateRegistro(placaVeiculo);
    }

}


//    @GetMapping(value = "/{id}")
//    public ResponseEntity<RegistroEstacionamento> findById(UUID id) {
//        log.info("Buscando registro.");
//        ResponseEntity<RegistroEstacionamento> response = estacionamentoRepository.findById(id);
//        return ResponseEntity.ok(response);
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<Object> findOneRegistro(@PathVariable(value = "id") String id) {
//        log.info("Buscando um registro de veículo.");
//        Optional<RegistroEstacionamento> estacionamentoOptional = estacionamentoService.findById(id);
//        if (estacionamentoOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONTINUE).body(estacionamentoOptional.get());
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há registros.");
//    }

//    @DeleteMapping
//    public ResponseEntity<List<RegistroEstacionamento>> findAll() {
//        log.info("Buscando todos os registros.");
//        List<RegistroEstacionamento> result = estacionamentoService.findAll();
//        return ResponseEntity.ok(result);
//    }


//    @GetMapping
//    public ResponseEntity<List<EstacionamentoModel>> getAllPlacas() {
//        log.info("Listando todos os veículos no estacionamento.");
//        return ResponseEntity.status(HttpStatus.OK).body(estacionamentoServiceImpl.findAll());
//    }
//
//    // TODO ARRUMAR VERIFICAÇAO A placa do carro deve ter exatamente 7 caracteres. //
//
//    @GetMapping("/{placaVeiculo}")
//    public ResponseEntity<Object> getPlaca(@PathVariable(value = "placaVeiculo") String placaVeiculo) {
//        Optional<EstacionamentoModel> estacionamentoModelOptional = estacionamentoServiceImpl.findByPlaca(placaVeiculo);
//        if (estacionamentoModelOptional.isEmpty()) {
//            log.info("Registro não encontrado.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro não encontrado.");
//        }
//        log.info("Resgistro encontrado " + placaVeiculo);
//        return ResponseEntity.status(HttpStatus.CONTINUE).body(estacionamentoServiceImpl.findByPlaca(placaVeiculo));
//    }

//        boolean estacionamentoOptional = estacionamentoService.existePlacaVeiculo(placaVeiculo);
//        if (estacionamentoOptional. ) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro de estacionamento não encontrado para a placa do veículo: " + placaVeiculo);
//        }
//
//        EstacionamentoModel estacionamento = estacionamentoOptional.get();
//        return ResponseEntity.status(HttpStatus.OK).body(estacionamento);
//    }


//    @GetMapping("/{placa}")
//    public ResponseEntity<Object> getOnePlaca(@PathVariable(value = "placa") String placa) {
//        String placaVeiculo = estacionamentoService.obterPlacaPorVeiculo(placa);
//        if (placaVeiculo.length() != 7) {
//            log.info("A placa do carro deve ter exatamente 7 caracteres.");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A placa do carro deve ter exatamente 7 caracteres.");
//        }
//
//        String estacionamentoModelOptional = String.valueOf(estacionamentoService.existePlacaVeiculo(placaVeiculo));
//        if (estacionamentoModelOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Placa de veículo não encontrada.");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(estacionamentoService.obterPlacaPorVeiculo(placaVeiculo));
//    }

//    @DeleteMapping("/{placaVeiculo}")
//    public ResponseEntity<Object> deletaPlaca(@RequestBody VagaEstacionamentoDto vagaEstacionamentoDto) {
//        if (vagaEstacionamentoDto.getPlacaVeiculo().isEmpty()) {
//            log.error("Não há placas com esse registro.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há placas com esse registro.");
//        } else {
//            log.info("Deletando registro" + vagaEstacionamentoDto.getPlacaVeiculo());
//            return ResponseEntity.status(HttpStatus.OK).body("Deletando registro de placa " + vagaEstacionamentoDto.getPlacaVeiculo());
//        }
//    }


//    @GetMapping("/{placa}")
//    public ResponseEntity<Object> getOnePlaca(@PathVariable(value = "placa") String placa) {
//        if (placa.length() != 7) {
//            log.info("A placa do carro deve ter exatamente 7 caracteres.");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A placa do carro deve ter exatamente 7 caracteres.");
//        }
//
//        String placaVeiculo = estacionamentoService.obterPlacaPorVeiculo(placa);
//        if (placaVeiculo.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Placa de veículo não encontrada.");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body("Placa de veículo encontrada");
//    }


