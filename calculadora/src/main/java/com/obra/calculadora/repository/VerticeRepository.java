package com.obra.calculadora.repository;

import com.obra.calculadora.domain.Vertice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerticeRepository extends JpaRepository<Vertice, Long> {
}