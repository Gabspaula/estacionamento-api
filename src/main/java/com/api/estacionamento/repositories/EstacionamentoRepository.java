package com.api.estacionamento.repositories;


import com.api.estacionamento.models.RegistroEstacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface EstacionamentoRepository extends JpaRepository<RegistroEstacionamento, String> {

    boolean existsByPlacaVeiculo(String placa_veiculo);
    Optional<RegistroEstacionamento> findByPlacaVeiculo(String placaVeiculo);

    void deleteById(UUID id);

    Optional<RegistroEstacionamento> findById(UUID id);


//    Optional<RegistroEstacionamento> findById(String placa_veiculo);
//
//    ResponseEntity<RegistroEstacionamento> getPlaca(String placa_veiculo);

//    @Query("SELECT obj FROM RegistroEstacionamento obj WHERE obj.placa_veiculo = :placa_veiuclo")
//    Page<RegistroEstacionamento> getPlaca(String placaVeiculo);

//    boolean existePorPlaca(String placaVeiculo);
//
//    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EstacionamentoModel e WHERE e.placaVeiculo = :placaVeiculo")
//    boolean existePlacaCarro(String placaVeiculo);
//
//    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EstacionamentoModel e WHERE e.placaVeiculo = :placaVeiculo")
//    boolean obterPlacaPorVeiculo(String placaVeiculo);
//
//    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EstacionamentoModel e WHERE e.placaVeiculo = :placaVeiculo")
//    Optional<EstacionamentoModel> findByPlaca(String placa);

}
