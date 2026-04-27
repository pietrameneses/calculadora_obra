package com.obra.calculadora.dto;

import lombok.Data;

import java.util.List;

@Data
public class FundacaoResponse {

    private Double volumeTotalM3;
    private List<VigaDetalhe> detalhes;
    private String formula;

    @Data
    public static class VigaDetalhe {
        private String nomeAresta;
        private Double comprimento;
        private Double largura;
        private Double altura;
        private Double volume;
    }
}