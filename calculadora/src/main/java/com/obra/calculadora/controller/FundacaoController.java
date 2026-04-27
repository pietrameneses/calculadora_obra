package com.obra.calculadora.controller;

import com.obra.calculadora.dto.FundacaoRequest;
import com.obra.calculadora.dto.FundacaoResponse;
import com.obra.calculadora.service.FundacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fundacao")
public class FundacaoController {

    private final FundacaoService fundacaoService;

    public FundacaoController(FundacaoService fundacaoService) {
        this.fundacaoService = fundacaoService;
    }

    @PostMapping("/concreto")
    public ResponseEntity<FundacaoResponse> calcularConcreto(
            @Valid @RequestBody FundacaoRequest request) {
        FundacaoResponse response = fundacaoService.calcularVolumeConcreto(request);
        return ResponseEntity.ok(response);
    }
}