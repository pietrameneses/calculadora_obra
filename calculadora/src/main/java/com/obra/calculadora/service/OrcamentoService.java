package com.obra.calculadora.service;

import com.obra.calculadora.domain.Orcamento;
import com.obra.calculadora.dto.FundacaoRequest;
import com.obra.calculadora.dto.FundacaoResponse;
import com.obra.calculadora.dto.TijoloRequest;
import com.obra.calculadora.dto.TijoloResponse;
import com.obra.calculadora.repository.OrcamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final FundacaoService fundacaoService;
    private final ParedeService paredeService;

    public OrcamentoService(OrcamentoRepository orcamentoRepository,
                            FundacaoService fundacaoService,
                            ParedeService paredeService) {
        this.orcamentoRepository = orcamentoRepository;
        this.fundacaoService = fundacaoService;
        this.paredeService = paredeService;
    }

    public Orcamento calcularESalvarConcreto(String nomeCliente, FundacaoRequest request) {
        FundacaoResponse response = fundacaoService.calcularVolumeConcreto(request);

        Orcamento orcamento = Orcamento.builder()
                .nomeCliente(nomeCliente)
                .tipo("CONCRETO")
                .resultado(response.getVolumeTotalM3())
                .unidade("m³")
                .formula(response.getFormula())
                .build();

        return orcamentoRepository.save(orcamento);
    }

    public Orcamento calcularESalvarTijolos(String nomeCliente, TijoloRequest request) {
        TijoloResponse response = paredeService.calcularTijolos(request);

        Orcamento orcamento = Orcamento.builder()
                .nomeCliente(nomeCliente)
                .tipo("TIJOLOS")
                .resultado(Double.valueOf(response.getTotalTijolos()))
                .unidade("unidades")
                .formula(response.getFormula())
                .build();

        return orcamentoRepository.save(orcamento);
    }

    public List<Orcamento> buscarTodos() {
        return orcamentoRepository.findAll();
    }

    public List<Orcamento> buscarPorCliente(String nomeCliente) {
        return orcamentoRepository.findByNomeClienteContainingIgnoreCase(nomeCliente);
    }

    public Optional<Orcamento> buscarPorNumero(String numeroOrcamento) {
        return orcamentoRepository.findByNumeroOrcamento(numeroOrcamento);
    }
}
