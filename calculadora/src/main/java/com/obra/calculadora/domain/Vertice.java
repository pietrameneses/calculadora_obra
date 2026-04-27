package com.obra.calculadora.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "vertices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vertice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do vértice é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double coordenadaX;

    @Column(nullable = false)
    private Double coordenadaY;
}
