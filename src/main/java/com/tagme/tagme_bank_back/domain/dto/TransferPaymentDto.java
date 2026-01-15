package com.tagme.tagme_bank_back.domain.dto;

public record TransferPaymentDto(
        AuthDto authDto,
        TransferDto originTransferDto,
        TransferDto destinationTransferDto,
        PayDto payDto
) {
}
