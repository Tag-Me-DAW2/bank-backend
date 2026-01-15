package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.BankAccountMapper;
import com.tagme.tagme_bank_back.controller.webModel.response.BankAccountResponse;
import com.tagme.tagme_bank_back.domain.service.BankAccountService;
import com.tagme.tagme_bank_back.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
    public ResponseEntity<List<BankAccountResponse>> getBankAccountByClientId(
            @RequestHeader("Authorization") String token,
            @PathVariable Long clientId) {

        token = token.replace("Bearer ", "");
        Claims claims = JwtUtil.parseClaims(token);

        Long userId = claims.get("clientId", Long.class);
        if (!userId.equals(clientId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<BankAccountResponse> bankAccountResponse = bankAccountService.getByClientId(clientId).stream().map(BankAccountMapper::fromBankAccountToBankAccountResponse).toList();
        return new ResponseEntity<>(bankAccountResponse, HttpStatus.OK);
    }
}
