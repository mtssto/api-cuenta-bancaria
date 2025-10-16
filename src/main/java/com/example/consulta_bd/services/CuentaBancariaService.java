package com.example.consulta_bd.services;

import com.example.consulta_bd.models.*;
import com.example.consulta_bd.repository.CuentaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaBancariaService {

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    @Transactional
    public CuentaBancaria crearCuentaBancaria(CuentaBancaria cuentaBancaria) {
        if (cuentaBancariaRepository.existsById(cuentaBancaria.getId())) {
            throw new RuntimeException("Cuenta bancaria ya existe con este ID");
        }
        return cuentaBancariaRepository.save(cuentaBancaria);
    }

    public List<CuentaBancaria> obtenerTodasLasCuentas() {
        return cuentaBancariaRepository.findAll();
    }

    public Optional<CuentaBancaria> obtenerCuentaPorId(Long id) {
        return cuentaBancariaRepository.findById(id);
    }

    @Transactional
    public CuentaBancaria actualizarCuentaBancaria(Long id, CuentaBancaria cuentaBancaria) {
        CuentaBancaria cuentaExistente = cuentaBancariaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta bancaria no encontrada"));
        cuentaExistente.setNumeroCuenta(cuentaBancaria.getNumeroCuenta());
        cuentaExistente.setSaldo(cuentaBancaria.getSaldo());
        cuentaExistente.setTitular(cuentaBancaria.getTitular());
        return cuentaBancariaRepository.save(cuentaExistente);
    }

    @Transactional
    public void eliminarCuentaBancaria(Long id) {
        CuentaBancaria cuentaExistente = cuentaBancariaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta bancaria no encontrada"));
        cuentaBancariaRepository.delete(cuentaExistente);
    }

    public List<CuentaBancaria> consultarCuentasInternamente() {
        // Llamada al propio servicio
        return obtenerTodasLasCuentas();
    }
}
