package com.obra.calculadora;

import com.obra.calculadora.dto.ArestaRequest;
import com.obra.calculadora.dto.FundacaoRequest;
import com.obra.calculadora.dto.FundacaoResponse;
import com.obra.calculadora.service.FundacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FundacaoServiceTest {

    @Autowired
    private FundacaoService fundacaoService;

    // Teste 1: volume correto com uma aresta
    @Test
    void deveCalcularVolumeComUmaAresta() {
        ArestaRequest aresta = new ArestaRequest();
        aresta.setNome("Parede Norte");
        aresta.setComprimento(5.0);

        FundacaoRequest request = new FundacaoRequest();
        request.setArestas(List.of(aresta));
        request.setLarguraViga(0.2);
        request.setAlturaViga(0.3);

        FundacaoResponse response = fundacaoService.calcularVolumeConcreto(request);

        // V = 0.2 × 0.3 × 5.0 = 0.300
        assertEquals(0.3, response.getVolumeTotalM3(), 0.001);
    }

    // Teste 2: volume correto com múltiplas arestas
    @Test
    void deveCalcularVolumeComMultiplasArestas() {
        ArestaRequest a1 = new ArestaRequest();
        a1.setNome("Parede Norte");
        a1.setComprimento(5.0);

        ArestaRequest a2 = new ArestaRequest();
        a2.setNome("Parede Sul");
        a2.setComprimento(3.0);

        FundacaoRequest request = new FundacaoRequest();
        request.setArestas(List.of(a1, a2));
        request.setLarguraViga(0.2);
        request.setAlturaViga(0.3);

        FundacaoResponse response = fundacaoService.calcularVolumeConcreto(request);

        // (0.2×0.3×5) + (0.2×0.3×3) = 0.3 + 0.18 = 0.48
        assertEquals(0.48, response.getVolumeTotalM3(), 0.001);
    }

    // Teste 3: detalhes preenchidos corretamente
    @Test
    void devePreencherDetalhesCorretos() {
        ArestaRequest aresta = new ArestaRequest();
        aresta.setNome("Parede Leste");
        aresta.setComprimento(4.0);

        FundacaoRequest request = new FundacaoRequest();
        request.setArestas(List.of(aresta));
        request.setLarguraViga(0.25);
        request.setAlturaViga(0.4);

        FundacaoResponse response = fundacaoService.calcularVolumeConcreto(request);

        assertEquals(1, response.getDetalhes().size());
        assertEquals("Parede Leste", response.getDetalhes().get(0).getNomeAresta());
        assertEquals(4.0, response.getDetalhes().get(0).getComprimento());
    }

    // Teste 4: fórmula retornada não é nula
    @Test
    void deveRetornarFormulaNaoNula() {
        ArestaRequest aresta = new ArestaRequest();
        aresta.setNome("Parede Oeste");
        aresta.setComprimento(6.0);

        FundacaoRequest request = new FundacaoRequest();
        request.setArestas(List.of(aresta));
        request.setLarguraViga(0.2);
        request.setAlturaViga(0.3);

        FundacaoResponse response = fundacaoService.calcularVolumeConcreto(request);

        assertNotNull(response.getFormula());
        assertFalse(response.getFormula().isEmpty());
    }
}