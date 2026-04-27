package com.obra.calculadora.repository;

import com.obra.calculadora.domain.Aresta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArestaRepository extends JpaRepository<Aresta, Long> {
    List<Aresta> findByNomeContainingIgnoreCase(String nome);
}