package com.tagme.tagme_bank_back.controller.mapper;

import com.tagme.tagme_bank_back.controller.webModel.request.PayRequest;
import com.tagme.tagme_bank_back.domain.dto.PayDto;

public class PayMapper {
    public static PayDto toDto(PayRequest request) {
        if (request == null) {
            return null;
        }

        return new PayDto(
                request.amount(),
                request.concept()
        );
    }
}
