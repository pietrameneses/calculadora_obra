package com.obra.calculadora.bean;

import com.obra.calculadora.domain.Orcamento;
import com.obra.calculadora.dto.ArestaRequest;
import com.obra.calculadora.dto.FundacaoRequest;
import com.obra.calculadora.dto.TijoloRequest;
import com.obra.calculadora.service.OrcamentoService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
@Data
public class OrcamentoBean implements Serializable {

    private final OrcamentoService orcamentoService;

    private String nomeCliente;
    private String tipoCalculo = "CONCRETO";

    // Viga
    private Double larguraViga;
    private Double alturaViga;

    // Tijolo
    private Double comprimentoTijolo;
    private Double alturaTijolo;
    private Double larguraTijolo;
    private Double fatorDesperdicio = 1.10;

    // Arestas
    private List<ArestaRequest> arestas = new ArrayList<>();

    // Resultado
    private Orcamento orcamentoSalvo;
    private List<Orcamento> orcamentosEncontrados = new ArrayList<>();
    private String buscaNome;
    private String buscaNumero;

    public OrcamentoBean(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
        adicionarAresta(); // começa com uma aresta
    }

    public void adicionarAresta() {
        ArestaRequest a = new ArestaRequest();
        a.setPossuiJanela(false);
        a.setPossuiPorta(false);
        arestas.add(a);
    }

    public void removerAresta(ArestaRequest aresta) {
        arestas.remove(aresta);
    }

    public void calcular() {
        if (nomeCliente == null || nomeCliente.isBlank()) {
            addErro("Nome do cliente é obrigatório!");
            return;
        }
        if (arestas.isEmpty()) {
            addErro("Adicione pelo menos uma parede!");
            return;
        }

        try {
            if ("CONCRETO".equals(tipoCalculo)) {
                FundacaoRequest request = new FundacaoRequest();
                request.setArestas(arestas);
                request.setLarguraViga(larguraViga);
                request.setAlturaViga(alturaViga);
                orcamentoSalvo = orcamentoService.calcularESalvarConcreto(nomeCliente, request);
            } else {
                TijoloRequest request = new TijoloRequest();
                request.setArestas(arestas);
                request.setComprimentoTijolo(comprimentoTijolo);
                request.setAlturaTijolo(alturaTijolo);
                request.setLarguraTijolo(larguraTijolo);
                request.setFatorDesperdicio(fatorDesperdicio);
                orcamentoSalvo = orcamentoService.calcularESalvarTijolos(nomeCliente, request);
            }
            addInfo("Orçamento salvo com sucesso! Número: " + orcamentoSalvo.getNumeroOrcamento());
        } catch (Exception e) {
            addErro("Erro ao calcular: " + e.getMessage());
        }
    }

    public void buscarPorNome() {
        orcamentosEncontrados = orcamentoService.buscarPorCliente(buscaNome);
    }

    public void buscarPorNumero() {
        orcamentosEncontrados = new ArrayList<>();
        orcamentoService.buscarPorNumero(buscaNumero)
                .ifPresent(orcamentosEncontrados::add);
    }

    private void addErro(String msg) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
    }

    private void addInfo(String msg) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }
}