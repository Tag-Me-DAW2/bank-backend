package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.BankAccountMapper;
import com.tagme.tagme_bank_back.controller.webModel.response.BankAccountResponse;
import com.tagme.tagme_bank_back.domain.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/bank-accounts")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountResponse> getBankAccountById(@PathVariable Long id) {
        BankAccountResponse bankAccountResponse = BankAccountMapper.fromBankAccountToBankAccountResponse(bankAccountService.getById(id));
        return new ResponseEntity<>(bankAccountResponse, HttpStatus.OK);
    }

    @GetMapping("/iban/{iban}")
    public ResponseEntity<BankAccountResponse> getBankAccountByIban(@PathVariable String iban) {
        BankAccountResponse bankAccountResponse = BankAccountMapper.fromBankAccountToBankAccountResponse(bankAccountService.getByIban(iban));
        return new ResponseEntity<>(bankAccountResponse, HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BankAccountResponse>> getBankAccountByClientId(@PathVariable Long clientId) {
        List<BankAccountResponse> bankAccountResponse = bankAccountService.getByClientId(clientId).stream().map(BankAccountMapper::fromBankAccountToBankAccountResponse).toList();
        return new ResponseEntity<>(bankAccountResponse, HttpStatus.OK);
    }
}
