package com.tagme.tagme_bank_back.controller.mapper;

import com.tagme.tagme_bank_back.controller.webModel.request.AuthRequest;
import com.tagme.tagme_bank_back.domain.dto.AuthDto;

public class AuthMapper {
    public static AuthDto toDto(AuthRequest authRequest) {
        if (authRequest == null) {
            return null;
        }

        return new AuthDto(
                authRequest.username(),
                authRequest.apiKey()
        );
    }
}
