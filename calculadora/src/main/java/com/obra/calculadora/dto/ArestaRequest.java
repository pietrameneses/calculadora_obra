package com.obra.calculadora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ArestaRequest {

    @NotBlank(message = "O nome da aresta é obrigatório")
    private String nome;

    @Positive(message = "O comprimento deve ser positivo")
    private Double comprimento;

    @Positive(message = "A espessura deve ser positiva")
    private Double espessura;

    @Positive(message = "A altura deve ser positiva")
    private Double altura;

    private Boolean possuiJanela = false;
    private Double larguraJanela;
    private Double alturaJanela;

    private Boolean possuiPorta = false;
    private Double larguraPorta;
    private Double alturaPorta;
}
