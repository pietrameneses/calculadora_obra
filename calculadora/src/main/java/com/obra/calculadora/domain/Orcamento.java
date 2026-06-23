package com.obra.calculadora.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orcamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCliente;

    @Column(nullable = false, unique = true)
    private String numeroOrcamento;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Double resultado;

    @Column(nullable = false)
    private String unidade;

    private String formula;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.numeroOrcamento = "ORC-" + System.currentTimeMillis();
    }
}