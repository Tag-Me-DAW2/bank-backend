package com.tagme.tagme_bank_back.controller.webModel.request;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "Username must not be blank")
        String username,
        @NotBlank(message = "Api Key must not be blank")
        String apiKey
) {
}
