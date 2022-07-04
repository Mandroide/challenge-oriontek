package com.interview.oriontekchallenge.repository;

import com.interview.oriontekchallenge.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query(
            value = "SELECT * from cliente_mostraractivos()",
            nativeQuery = true)
    Optional<List<Cliente>> findAllByEstatusActivo();

    @Query(
            value = "SELECT * from cliente_buscaractivos(?)",
            nativeQuery = true)
    Optional<List<Cliente>> findAllByEstatusActivoAndName(String name);

}
