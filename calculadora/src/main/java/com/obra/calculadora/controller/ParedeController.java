package com.obra.calculadora.controller;

import com.obra.calculadora.dto.TijoloRequest;
import com.obra.calculadora.dto.TijoloResponse;
import com.obra.calculadora.service.ParedeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paredes")
public class ParedeController {

    private final ParedeService paredeService;

    public ParedeController(ParedeService paredeService) {
        this.paredeService = paredeService;
    }

    @PostMapping("/tijolos")
    public ResponseEntity<TijoloResponse> calcularTijolos(
            @Valid @RequestBody TijoloRequest request) {
        TijoloResponse response = paredeService.calcularTijolos(request);
        return ResponseEntity.ok(response);
    }
}