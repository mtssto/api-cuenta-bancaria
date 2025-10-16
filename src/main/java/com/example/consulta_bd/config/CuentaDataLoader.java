package com.example.consulta_bd.config;

import com.example.consulta_bd.models.CuentaBancaria;
import com.example.consulta_bd.repository.CuentaBancariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CuentaDataLoader implements CommandLineRunner {
    private final CuentaBancariaRepository repo;
    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            repo.save(CuentaBancaria.builder().numeroCuenta("AR12 3456 7890 1234").saldo(100_000).titular("Juan Pérez").build());
            repo.save(CuentaBancaria.builder().numeroCuenta("AR55 9999 0000 2222").saldo(50_500).titular("María Gómez").build());
        }
    }
}
