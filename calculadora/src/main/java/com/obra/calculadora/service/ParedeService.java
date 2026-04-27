package com.obra.calculadora.service;

import com.obra.calculadora.dto.ArestaRequest;
import com.obra.calculadora.dto.TijoloRequest;
import com.obra.calculadora.dto.TijoloResponse;
import com.obra.calculadora.dto.TijoloResponse.ParedeDetalhe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParedeService {

    public TijoloResponse calcularTijolos(TijoloRequest request) {
        double areaTijolo = request.getComprimentoTijolo() * request.getAlturaTijolo();
        double fator      = request.getFatorDesperdicio() != null ? request.getFatorDesperdicio() : 1.10;

        List<ParedeDetalhe> detalhes = new ArrayList<>();
        double areaTotalLiquida = 0.0;
        int totalTijolos = 0;

        for (ArestaRequest aresta : request.getArestas()) {
            double areaTotal = aresta.getComprimento() * aresta.getAltura();

            double areaDesconto = 0.0;
            if (Boolean.TRUE.equals(aresta.getPossuiJanela())
                    && aresta.getLarguraJanela() != null && aresta.getAlturaJanela() != null) {
                areaDesconto += aresta.getLarguraJanela() * aresta.getAlturaJanela();
            }
            if (Boolean.TRUE.equals(aresta.getPossuiPorta())
                    && aresta.getLarguraPorta() != null && aresta.getAlturaPorta() != null) {
                areaDesconto += aresta.getLarguraPorta() * aresta.getAlturaPorta();
            }

            double areaLiquida = Math.max(0.0, areaTotal - areaDesconto);
            int tijolosParede  = (int) Math.ceil((areaLiquida / areaTijolo) * fator);

            areaTotalLiquida += areaLiquida;
            totalTijolos     += tijolosParede;

            ParedeDetalhe detalhe = new ParedeDetalhe();
            detalhe.setNomeAresta(aresta.getNome());
            detalhe.setComprimento(aresta.getComprimento());
            detalhe.setAltura(aresta.getAltura());
            detalhe.setAreaDesconto(arredondar(areaDesconto));
            detalhe.setAreaLiquida(arredondar(areaLiquida));
            detalhe.setTijolosPorParede(tijolosParede);

            detalhes.add(detalhe);
        }

        TijoloResponse response = new TijoloResponse();
        response.setTotalTijolos(totalTijolos);
        response.setAreaTotalLiquidaM2(arredondar(areaTotalLiquida));
        response.setAreaTijoloM2(arredondar(areaTijolo));
        response.setFatorDesperdicio(fator);
        response.setDetalhes(detalhes);
        response.setFormula("N = ceil((Área_líquida / Área_tijolo) × fator_desperdício)");

        return response;
    }

    private double arredondar(double valor) {
        return Math.round(valor * 1000.0) / 1000.0;
    }
}