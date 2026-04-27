package com.obra.calculadora;

import com.obra.calculadora.dto.ArestaRequest;
import com.obra.calculadora.dto.TijoloRequest;
import com.obra.calculadora.dto.TijoloResponse;
import com.obra.calculadora.service.ParedeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ParedeServiceTest {

    @Autowired
    private ParedeService paredeService;

    private ArestaRequest arestaSimples(String nome, double comprimento, double altura) {
        ArestaRequest a = new ArestaRequest();
        a.setNome(nome);
        a.setComprimento(comprimento);
        a.setAltura(altura);
        return a;
    }

    // Teste 1: parede sem janela e sem porta
    @Test
    void deveCaclularTijolosSemAberturas() {
        TijoloRequest request = new TijoloRequest();
        request.setArestas(List.of(arestaSimples("P1", 5.0, 3.0)));
        request.setComprimentoTijolo(0.19);
        request.setAlturaTijolo(0.09);
        request.setFatorDesperdicio(1.0); // sem desperdício para facilitar

        TijoloResponse response = paredeService.calcularTijolos(request);

        // área = 15m², tijolo = 0.0171m², N = ceil(15/0.0171) = 878
        assertTrue(response.getTotalTijolos() > 0);
        assertEquals(15.0, response.getAreaTotalLiquidaM2(), 0.01);
    }

    // Teste 2: parede com janela desconta área
    @Test
    void deveDescontarAreaDaJanela() {
        ArestaRequest aresta = arestaSimples("P2", 5.0, 3.0);
        aresta.setPossuiJanela(true);
        aresta.setLarguraJanela(1.2);
        aresta.setAlturaJanela(1.0);

        TijoloRequest request = new TijoloRequest();
        request.setArestas(List.of(aresta));
        request.setComprimentoTijolo(0.19);
        request.setAlturaTijolo(0.09);
        request.setFatorDesperdicio(1.0);

        TijoloResponse response = paredeService.calcularTijolos(request);

        // área líquida = 15 - 1.2 = 13.8
        assertEquals(13.8, response.getAreaTotalLiquidaM2(), 0.01);
    }

    // Teste 3: parede com porta desconta área
    @Test
    void deveDescontarAreaDaPorta() {
        ArestaRequest aresta = arestaSimples("P3", 4.0, 3.0);
        aresta.setPossuiPorta(true);
        aresta.setLarguraPorta(0.9);
        aresta.setAlturaPorta(2.1);

        TijoloRequest request = new TijoloRequest();
        request.setArestas(List.of(aresta));
        request.setComprimentoTijolo(0.19);
        request.setAlturaTijolo(0.09);
        request.setFatorDesperdicio(1.0);

        TijoloResponse response = paredeService.calcularTijolos(request);

        // área líquida = 12 - 1.89 = 10.11
        assertEquals(10.11, response.getAreaTotalLiquidaM2(), 0.01);
    }

    // Teste 4: fator de desperdício aplicado
    @Test
    void deveAplicarFatorDesperdicio() {
        TijoloRequest semFator = new TijoloRequest();
        semFator.setArestas(List.of(arestaSimples("P4", 5.0, 3.0)));
        semFator.setComprimentoTijolo(0.19);
        semFator.setAlturaTijolo(0.09);
        semFator.setFatorDesperdicio(1.0);

        TijoloRequest comFator = new TijoloRequest();
        comFator.setArestas(List.of(arestaSimples("P4", 5.0, 3.0)));
        comFator.setComprimentoTijolo(0.19);
        comFator.setAlturaTijolo(0.09);
        comFator.setFatorDesperdicio(1.10);

        int semDesperdicio = paredeService.calcularTijolos(semFator).getTotalTijolos();
        int comDesperdicio = paredeService.calcularTijolos(comFator).getTotalTijolos();

        assertTrue(comDesperdicio >= semDesperdicio);
    }

    // Teste 5: fator padrão 1.10 quando não informado
    @Test
    void deveUsarFatorPadraoQuandoNulo() {
        TijoloRequest request = new TijoloRequest();
        request.setArestas(List.of(arestaSimples("P5", 5.0, 3.0)));
        request.setComprimentoTijolo(0.19);
        request.setAlturaTijolo(0.09);
        request.setFatorDesperdicio(null);

        TijoloResponse response = paredeService.calcularTijolos(request);

        assertEquals(1.10, response.getFatorDesperdicio(), 0.001);
    }

    // Teste 6: múltiplas arestas somam corretamente
    @Test
    void deveSomarMultiplasArestas() {
        TijoloRequest request = new TijoloRequest();
        request.setArestas(List.of(
                arestaSimples("P6a", 3.0, 3.0),
                arestaSimples("P6b", 2.0, 3.0)
        ));
        request.setComprimentoTijolo(0.19);
        request.setAlturaTijolo(0.09);
        request.setFatorDesperdicio(1.0);

        TijoloResponse response = paredeService.calcularTijolos(request);

        // área total = 9 + 6 = 15
        assertEquals(15.0, response.getAreaTotalLiquidaM2(), 0.01);
    }

    // Teste 7: fórmula não é nula
    @Test
    void deveRetornarFormulaNaoNula() {
        TijoloRequest request = new TijoloRequest();
        request.setArestas(List.of(arestaSimples("P7", 4.0, 3.0)));
        request.setComprimentoTijolo(0.19);
        request.setAlturaTijolo(0.09);
        request.setFatorDesperdicio(1.0);

        TijoloResponse response = paredeService.calcularTijolos(request);

        assertNotNull(response.getFormula());
    }
}
