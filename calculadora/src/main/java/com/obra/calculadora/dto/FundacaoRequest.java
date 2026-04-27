package com.obra.calculadora.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class FundacaoRequest {

    @NotEmpty(message = "Informe ao menos uma aresta")
    @Valid
    private List<ArestaRequest> arestas;

    @NotNull
    @Positive(message = "A altura da viga deve ser positiva")
    private Double alturaViga;

    @NotNull
    @Positive(message = "A largura da viga deve ser positiva")
    private Double larguraViga;
}