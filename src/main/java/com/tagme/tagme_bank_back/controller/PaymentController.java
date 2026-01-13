package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.PaymentMapper;
import com.tagme.tagme_bank_back.controller.webModel.request.CreditCardPaymentRequest;
import com.tagme.tagme_bank_back.controller.webModel.response.PaymentResponse;
import com.tagme.tagme_bank_back.domain.dto.CreditCardPaymentDto;
import com.tagme.tagme_bank_back.domain.service.BankAccountService;
import com.tagme.tagme_bank_back.domain.service.ClientService;
import com.tagme.tagme_bank_back.domain.service.PaymentService;
import com.tagme.tagme_bank_back.domain.validation.DtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@CrossOrigin("*")
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/credit-card")
    public ResponseEntity<PaymentResponse> processCreditCarPayment(@RequestBody CreditCardPaymentRequest request) {
        DtoValidator.validate(request);

        CreditCardPaymentDto paymentDto = PaymentMapper.fromCreditCardPaymentRequest(request);

        BigDecimal newBalance = paymentService.processCreditCardPayment(paymentDto);

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

}
