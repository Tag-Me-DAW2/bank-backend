package com.tagme.tagme_bank_back.controller.webModel.response;

public record ClientResponse(
        Long id,
        String username,
        String name,
        String lastName1,
        String lastName2,
        String dni
) {
}
