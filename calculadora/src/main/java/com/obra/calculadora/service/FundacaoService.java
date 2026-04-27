package com.obra.calculadora.service;

import com.obra.calculadora.dto.ArestaRequest;
import com.obra.calculadora.dto.FundacaoRequest;
import com.obra.calculadora.dto.FundacaoResponse;
import com.obra.calculadora.dto.FundacaoResponse.VigaDetalhe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FundacaoService {

    public FundacaoResponse calcularVolumeConcreto(FundacaoRequest request) {
        List<VigaDetalhe> detalhes = new ArrayList<>();
        double volumeTotal = 0.0;

        for (ArestaRequest aresta : request.getArestas()) {
            double comprimento = aresta.getComprimento();
            double largura     = request.getLarguraViga();
            double altura      = request.getAlturaViga();

            double volume = largura * altura * comprimento;
            volumeTotal += volume;

            VigaDetalhe detalhe = new VigaDetalhe();
            detalhe.setNomeAresta(aresta.getNome());
            detalhe.setComprimento(comprimento);
            detalhe.setLargura(largura);
            detalhe.setAltura(altura);
            detalhe.setVolume(arredondar(volume));

            detalhes.add(detalhe);
        }

        FundacaoResponse response = new FundacaoResponse();
        response.setVolumeTotalM3(arredondar(volumeTotal));
        response.setDetalhes(detalhes);
        response.setFormula("V = L × A × C  |  L=" + request.getLarguraViga()
                + "m, A=" + request.getAlturaViga() + "m, C=comprimento da aresta");

        return response;
    }

    private double arredondar(double valor) {
        return Math.round(valor * 1000.0) / 1000.0;
    }
}