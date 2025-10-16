package com.example.consulta_bd.mapper;

import com.example.consulta_bd.dto.CuentaRequest;
import com.example.consulta_bd.dto.CuentaResponse;
import com.example.consulta_bd.models.CuentaBancaria;

public class CuentaMapper {
    public static CuentaBancaria toEntity(CuentaRequest req) {
        return CuentaBancaria.builder()
                .numeroCuenta(req.numeroCuenta().replaceAll("\s+", "").toUpperCase())
                .saldo(req.saldo())
                .titular(req.titular().trim())
                .build();
    }
    public static void update(CuentaBancaria c, CuentaRequest req) {
        c.setNumeroCuenta(req.numeroCuenta().replaceAll("\s+", "").toUpperCase());
        c.setSaldo(req.saldo());
        c.setTitular(req.titular().trim());
    }
    public static CuentaResponse toDto(CuentaBancaria c) {
        return new CuentaResponse(c.getId(), c.getNumeroCuenta(), c.getSaldo(), c.getTitular());
    }
}
