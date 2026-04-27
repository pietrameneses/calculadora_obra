package com.obra.calculadora.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "arestas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aresta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da aresta é obrigatório")
    @Column(nullable = false)
    private String nome;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vertice_origem_id", nullable = false)
    private Vertice verticeOrigem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vertice_destino_id", nullable = false)
    private Vertice verticeDestino;

    @Positive(message = "O comprimento deve ser positivo")
    @Column(nullable = false)
    private Double comprimento;

    @Positive(message = "A espessura deve ser positiva")
    @Column(nullable = false)
    private Double espessura;

    @Positive(message = "A altura deve ser positiva")
    @Column(nullable = false)
    private Double altura;

    @Column(nullable = false)
    private Boolean possuiJanela;

    private Double larguraJanela;
    private Double alturaJanela;

    @Column(nullable = false)
    private Boolean possuiPorta;

    private Double larguraPorta;
    private Double alturaPorta;

    public double calcularAreaLiquida() {
        double areaTotal = comprimento * altura;
        double areaDescontos = 0.0;

        if (Boolean.TRUE.equals(possuiJanela) && larguraJanela != null && alturaJanela != null) {
            areaDescontos += larguraJanela * alturaJanela;
        }
        if (Boolean.TRUE.equals(possuiPorta) && larguraPorta != null && alturaPorta != null) {
            areaDescontos += larguraPorta * alturaPorta;
        }

        return Math.max(0.0, areaTotal - areaDescontos);
    }
}