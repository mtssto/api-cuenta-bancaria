package com.example.consulta_bd.services;

import com.example.consulta_bd.dto.CuentaRequest;
import com.example.consulta_bd.dto.CuentaResponse;
import com.example.consulta_bd.dto.PageResponse;

public interface CuentaService {
    CuentaResponse create(CuentaRequest req);
    CuentaResponse get(Long id);
    PageResponse<CuentaResponse> list(Integer page, Integer size, String q);
    CuentaResponse update(Long id, CuentaRequest req);
    void delete(Long id);
}
