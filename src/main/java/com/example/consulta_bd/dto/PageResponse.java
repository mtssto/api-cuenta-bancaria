package com.example.consulta_bd.dto;

import java.util.List;

public record PageResponse<T>(List<T> content, int page, int size,
                              long totalElements, int totalPages) {}
