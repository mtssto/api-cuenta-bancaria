package com.example.consulta_bd.controllers;

import com.example.consulta_bd.dto.CuentaRequest;
import com.example.consulta_bd.dto.CuentaResponse;
import com.example.consulta_bd.dto.PageResponse;
import com.example.consulta_bd.services.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class BancoController {

    private final CuentaService service;
    private final WebClient webClient;


    @PostMapping
    public ResponseEntity<CuentaResponse> create(@Valid @RequestBody CuentaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }


    @GetMapping("/{id}")
    public CuentaResponse get(@PathVariable Long id) { return service.get(id); }


    @GetMapping
    public PageResponse<CuentaResponse> list(@RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer size,
                                             @RequestParam(required = false) String q) {
        return service.list(page, size, q);
    }


    @PutMapping("/{id}")
    public CuentaResponse update(@PathVariable Long id, @Valid @RequestBody CuentaRequest req) {
        return service.update(id, req);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }


    @GetMapping("/summary")
    public Map<String, Object> summary() {
        var page = webClient.get().uri(uriBuilder -> uriBuilder.path("/api/cuentas").queryParam("size", 1000).build())
                .retrieve().bodyToMono(Map.class).block();
        var content = (java.util.List<Map<String, Object>>) page.get("content");
        long total = ((Number) page.get("totalElements")).longValue();
        double saldoTotal = content.stream()
                .mapToDouble(m -> ((Number) m.get("saldo")).doubleValue())
                .sum();
        return Map.of("totalCuentas", total, "saldoTotal", saldoTotal);
    }
}
