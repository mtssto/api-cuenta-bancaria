package com.example.consulta_bd.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CuentaRequest(
        @NotBlank @Size(max = 34) String numeroCuenta,
        @DecimalMin(value = "0.0", inclusive = true, message = "El saldo no puede ser negativo") double saldo,
        @NotBlank @Size(max = 120) String titular
) {}