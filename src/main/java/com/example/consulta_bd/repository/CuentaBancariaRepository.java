package com.example.consulta_bd.repository;


import com.example.consulta_bd.models.CuentaBancaria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Long> {
    boolean existsByNumeroCuenta(String numeroCuenta);
    Page<CuentaBancaria> findByTitularContainingIgnoreCase(String q, Pageable pageable);
}
