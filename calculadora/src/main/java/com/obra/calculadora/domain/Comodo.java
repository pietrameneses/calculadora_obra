package com.obra.calculadora.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comodos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cômodo é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Positive
    @Column(nullable = false)
    private Double largura;

    @Positive
    @Column(nullable = false)
    private Double comprimento;

    @Positive
    @Column(nullable = false)
    private Double altura;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "comodo_arestas",
            joinColumns = @JoinColumn(name = "comodo_id"),
            inverseJoinColumns = @JoinColumn(name = "aresta_id")
    )
    @Builder.Default
    private List<Aresta> paredes = new ArrayList<>();

    public double calcularAreaPiso() {
        return largura * comprimento;
    }

    public double calcularAreaTotalParedes() {
        return paredes.stream()
                .mapToDouble(a -> a.getComprimento() * a.getAltura())
                .sum();
    }
}