package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.CreditCardMapper;
import com.tagme.tagme_bank_back.controller.mapper.MovementMapper;
import com.tagme.tagme_bank_back.controller.webModel.response.CreditCardResponse;
import com.tagme.tagme_bank_back.controller.webModel.response.summary.MovementSummaryResponse;
import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.model.Page;
import com.tagme.tagme_bank_back.domain.service.CreditCardService;
import com.tagme.tagme_bank_back.domain.service.MovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit-cards")
@CrossOrigin("*")
public class CreditCardController {
    private final CreditCardService creditCardService;
    private final MovementService movementService;

    public CreditCardController(CreditCardService creditCardService, MovementService movementService) {
        this.creditCardService = creditCardService;
        this.movementService = movementService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardResponse> getCreditCardById(@PathVariable Long id) {
        CreditCardResponse creditCardResponse = CreditCardMapper.fromCreditCardToCreditCardResponse(creditCardService.getById(id));
        return new ResponseEntity<>(creditCardResponse, HttpStatus.OK);
    }

    @GetMapping("/movements/{id}")
    public ResponseEntity<Page<MovementSummaryResponse>> getCreditCardMovements(@PathVariable Long id,
                                                                                @RequestParam(required = false, defaultValue = "1") int page,
                                                                                @RequestParam(required = false, defaultValue = "10") int size) {

        Page<Movement> movementPage = movementService.getByCardId(id, page, size);
        List<MovementSummaryResponse> movementSummaries = movementPage.data().stream()
                .map(MovementMapper::fromMovementToMovementSummaryResponse)
                .toList();

        Page<MovementSummaryResponse> responsePage = new Page<>(movementSummaries, page, size,
                movementPage.totalElements(), movementPage.totalPages());
        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }
}
