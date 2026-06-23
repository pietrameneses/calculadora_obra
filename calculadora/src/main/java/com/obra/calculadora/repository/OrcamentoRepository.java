package com.obra.calculadora.repository;

import com.obra.calculadora.domain.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {

    List<Orcamento> findByNomeClienteContainingIgnoreCase(String nomeCliente);

    Optional<Orcamento> findByNumeroOrcamento(String numeroOrcamento);
}