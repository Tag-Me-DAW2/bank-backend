package com.tagme.tagme_bank_back.controller.mapper;

import com.tagme.tagme_bank_back.controller.webModel.request.TransferRequest;
import com.tagme.tagme_bank_back.domain.dto.TransferDto;

public class TransferMapper {
    public static TransferDto toDto(TransferRequest request) {
        if (request == null) {
            return null;
        }

        return new TransferDto(
                request.iban()
        );
    }
}
