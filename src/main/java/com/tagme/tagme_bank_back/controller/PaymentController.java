package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.PaymentMapper;
import com.tagme.tagme_bank_back.controller.webModel.request.CreditCardPaymentRequest;
import com.tagme.tagme_bank_back.controller.webModel.response.PaymentResponse;
import com.tagme.tagme_bank_back.domain.dto.CreditCardPaymentDto;
import com.tagme.tagme_bank_back.domain.validation.DtoValidator;
import com.tagme.tagme_bank_back.usecase.CreditCardPaymentUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@CrossOrigin("*")
@RequestMapping("/payments")
public class PaymentController {
    private final CreditCardPaymentUseCase creditCardPaymentUseCase;

    public PaymentController(CreditCardPaymentUseCase creditCardPaymentUseCase) {
        this.creditCardPaymentUseCase = creditCardPaymentUseCase;
    }

    @PostMapping("/credit-card")
    public ResponseEntity<PaymentResponse> processCreditCarPayment(@RequestBody CreditCardPaymentRequest request) {
        DtoValidator.validate(request);

        CreditCardPaymentDto paymentDto = PaymentMapper.fromCreditCardPaymentRequest(request);

        creditCardPaymentUseCase.execute(paymentDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
