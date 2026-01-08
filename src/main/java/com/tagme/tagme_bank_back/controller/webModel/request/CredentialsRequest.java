package com.tagme.tagme_bank_back.controller.webModel.request;

import jakarta.validation.constraints.NotBlank;

public record CredentialsRequest(
    @NotBlank(message = "Username is required")
    String username, 
    @NotBlank(message = "Api key is required")
    String apiKey
) {
    
}
