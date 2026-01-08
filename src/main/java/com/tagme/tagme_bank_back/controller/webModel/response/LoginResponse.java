package com.tagme.tagme_bank_back.controller.webModel.response;

public record LoginResponse(
        ClientResponse clientResponse,
        String token
) {
}
