package com.example.consulta_bd.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cuentas", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cuenta_numero", columnNames = {"numero_cuenta"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "numero_cuenta", nullable = false, length = 34) // IBAN máx 34
    private String numeroCuenta;


    @Column(nullable = false)
    private double saldo;


    @Column(nullable = false, length = 120)
    private String titular;

}

