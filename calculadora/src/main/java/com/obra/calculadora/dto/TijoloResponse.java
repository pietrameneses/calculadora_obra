package com.obra.calculadora.dto;

import lombok.Data;

import java.util.List;

@Data
public class TijoloResponse {

    private Integer totalTijolos;
    private Double areaTotalLiquidaM2;
    private Double areaTijoloM2;
    private Double fatorDesperdicio;
    private List<ParedeDetalhe> detalhes;
    private String formula;

    @Data
    public static class ParedeDetalhe {
        private String nomeAresta;
        private Double comprimento;
        private Double altura;
        private Double areaDesconto;
        private Double areaLiquida;
        private Integer tijolosPorParede;
    }
}