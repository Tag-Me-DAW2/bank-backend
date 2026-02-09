package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.PaymentMapper;
import com.tagme.tagme_bank_back.controller.webModel.request.CreditCardPaymentRequest;
import com.tagme.tagme_bank_back.controller.webModel.request.TransferPaymentRequest;
import com.tagme.tagme_bank_back.controller.webModel.response.PaymentResponse;
import com.tagme.tagme_bank_back.domain.dto.CreditCardPaymentDto;
import com.tagme.tagme_bank_back.domain.dto.TransferPaymentDto;
import com.tagme.tagme_bank_back.domain.validation.DtoValidator;
import com.tagme.tagme_bank_back.usecase.CreditCardPaymentUseCase;
import com.tagme.tagme_bank_back.usecase.TransferPaymentUseCase;
import com.tagme.tagme_bank_back.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.testcontainers.shaded.org.bouncycastle.cert.ocsp.Req;

import java.math.BigDecimal;

@RestController
@CrossOrigin("*")
@RequestMapping("/payments")
public class PaymentController {
    private final CreditCardPaymentUseCase creditCardPaymentUseCase;
    private final TransferPaymentUseCase transferPaymentUseCase;

    public PaymentController(CreditCardPaymentUseCase creditCardPaymentUseCase, TransferPaymentUseCase transferPaymentUseCase) {
        this.transferPaymentUseCase = transferPaymentUseCase;
        this.creditCardPaymentUseCase = creditCardPaymentUseCase;
    }

    @PostMapping("/credit-card")
    public ResponseEntity<Void> processCreditCarPayment(@RequestBody CreditCardPaymentRequest request) {
        DtoValidator.validate(request);

        CreditCardPaymentDto paymentDto = PaymentMapper.fromCreditCardPaymentRequest(request);
        System.out.println(request);

        creditCardPaymentUseCase.execute(paymentDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> processTransferPayment(
            @RequestHeader("Authorization") String token,
            @RequestBody TransferPaymentRequest request

    ) {
        String jwtToken = token.substring(7);
        if(!JwtUtil.validateToken(jwtToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        DtoValidator.validate(request);

        TransferPaymentDto paymentDto = PaymentMapper.fromTransferPaymentRequest(request);

        transferPaymentUseCase.execute(paymentDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
