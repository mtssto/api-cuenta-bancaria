package com.example.consulta_bd.services.imp;

import com.example.consulta_bd.dto.CuentaRequest;
import com.example.consulta_bd.dto.CuentaResponse;
import com.example.consulta_bd.dto.PageResponse;
import com.example.consulta_bd.exception.DuplicateException;
import com.example.consulta_bd.exception.NotFoundException;
import com.example.consulta_bd.mapper.CuentaMapper;
import com.example.consulta_bd.repository.CuentaBancariaRepository;
import com.example.consulta_bd.services.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaBancariaRepository repo;

    @Override
    public CuentaResponse create(CuentaRequest req) {
        if (repo.existsByNumeroCuenta(req.numeroCuenta().replaceAll("\s+", "").toUpperCase()))
            throw new DuplicateException("Ya existe una cuenta con ese número");
        var saved = repo.save(CuentaMapper.toEntity(req));
        return CuentaMapper.toDto(saved);
    }


    @Override
    public CuentaResponse get(Long id) {
        var c = repo.findById(id).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        return CuentaMapper.toDto(c);
    }


    @Override
    public PageResponse<CuentaResponse> list(Integer page, Integer size, String q) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, size == null ? 10 : size);
        var p = (q != null && !q.isBlank()) ? repo.findByTitularContainingIgnoreCase(q, pageable) : repo.findAll(pageable);
        var content = p.stream().map(CuentaMapper::toDto).collect(Collectors.toList());
        return new PageResponse<>(content, p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }


    @Override
    public CuentaResponse update(Long id, CuentaRequest req) {
        var c = repo.findById(id).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        var nuevoNumero = req.numeroCuenta().replaceAll("\s+", "").toUpperCase();
        if (!c.getNumeroCuenta().equalsIgnoreCase(nuevoNumero) && repo.existsByNumeroCuenta(nuevoNumero))
            throw new DuplicateException("Número de cuenta duplicado");
        CuentaMapper.update(c, req);
        return CuentaMapper.toDto(repo.save(c));
    }


    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Cuenta no encontrada");
        repo.deleteById(id);
    }
}
