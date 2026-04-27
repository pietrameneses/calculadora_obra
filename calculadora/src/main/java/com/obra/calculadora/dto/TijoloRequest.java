package com.obra.calculadora.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class TijoloRequest {

    @NotEmpty(message = "Informe ao menos uma aresta")
    @Valid
    private List<ArestaRequest> arestas;

    @NotNull
    @Positive(message = "A altura do tijolo deve ser positiva")
    private Double alturaTijolo;

    @NotNull
    @Positive(message = "A largura do tijolo deve ser positiva")
    private Double larguraTijolo;

    @NotNull
    @Positive(message = "O comprimento do tijolo deve ser positivo")
    private Double comprimentoTijolo;

    private Double fatorDesperdicio = 1.10;
}