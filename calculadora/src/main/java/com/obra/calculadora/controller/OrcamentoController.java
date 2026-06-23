package com.obra.calculadora.controller;

import com.obra.calculadora.domain.Orcamento;
import com.obra.calculadora.dto.FundacaoRequest;
import com.obra.calculadora.dto.TijoloRequest;
import com.obra.calculadora.service.OrcamentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    public OrcamentoController(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    @PostMapping("/concreto/{nomeCliente}")
    public ResponseEntity<Orcamento> salvarConcreto(
            @PathVariable String nomeCliente,
            @Valid @RequestBody FundacaoRequest request) {
        Orcamento orcamento = orcamentoService.calcularESalvarConcreto(nomeCliente, request);
        return ResponseEntity.ok(orcamento);
    }

    @PostMapping("/tijolos/{nomeCliente}")
    public ResponseEntity<Orcamento> salvarTijolos(
            @PathVariable String nomeCliente,
            @Valid @RequestBody TijoloRequest request) {
        Orcamento orcamento = orcamentoService.calcularESalvarTijolos(nomeCliente, request);
        return ResponseEntity.ok(orcamento);
    }

    @GetMapping
    public ResponseEntity<List<Orcamento>> buscarTodos() {
        return ResponseEntity.ok(orcamentoService.buscarTodos());
    }

    @GetMapping("/cliente/{nomeCliente}")
    public ResponseEntity<List<Orcamento>> buscarPorCliente(
            @PathVariable String nomeCliente) {
        return ResponseEntity.ok(orcamentoService.buscarPorCliente(nomeCliente));
    }

    @GetMapping("/numero/{numeroOrcamento}")
    public ResponseEntity<Orcamento> buscarPorNumero(
            @PathVariable String numeroOrcamento) {
        return orcamentoService.buscarPorNumero(numeroOrcamento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
