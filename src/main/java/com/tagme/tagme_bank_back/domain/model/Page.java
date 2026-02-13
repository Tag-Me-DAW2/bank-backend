package com.tagme.tagme_bank_back.domain.model;

import java.util.List;

public record Page<T>(
        List<T> data,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {

    public Page(List<T> data, int pageNumber, int pageSize, long totalElements) {
        this(
                validateDataSize(data, pageSize),
                validatePageNumber(pageNumber),
                validatePageSize(pageSize),
                totalElements,
                (int) Math.ceil((double) totalElements / pageSize)
        );
    }

    private static <T> List<T> validateDataSize(List<T> data, int pageSize) {
        if (data.size() > pageSize) {
            throw new RuntimeException("El tamaño de los datos no puede ser mayor que el tamaño de la página");
        }
        return data;
    }

    private static int validatePageNumber(int pageNumber) {
        if (pageNumber < 1) {
            throw new RuntimeException("El número de página no puede ser menor que uno");
        }
        return pageNumber;
    }

    private static int validatePageSize(int pageSize) {
        if (pageSize <= 0) {
            throw new RuntimeException("El tamaño de la página debe ser mayor que cero");
        }
        return pageSize;
    }
}
