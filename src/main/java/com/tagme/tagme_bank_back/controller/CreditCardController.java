package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.CreditCardMapper;
import com.tagme.tagme_bank_back.controller.webModel.response.CreditCardResponse;
import com.tagme.tagme_bank_back.domain.service.CreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit-cards")
@CrossOrigin("*")
public class CreditCardController {
    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardResponse> getCreditCardById(@PathVariable Long id) {
        CreditCardResponse creditCardResponse = CreditCardMapper.fromCreditCardToCreditCardResponse(creditCardService.getById(id));
        return new ResponseEntity<>(creditCardResponse, HttpStatus.OK);
    }
}
