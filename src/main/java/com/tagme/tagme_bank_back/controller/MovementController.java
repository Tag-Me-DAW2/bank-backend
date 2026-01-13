package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.MovementMapper;
import com.tagme.tagme_bank_back.controller.webModel.response.MovementResponse;
import com.tagme.tagme_bank_back.controller.webModel.response.summary.MovementSummaryResponse;
import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.model.Page;
import com.tagme.tagme_bank_back.domain.service.MovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/movements")
public class MovementController {
    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementResponse> getMovementById(@PathVariable Long id) {
        MovementResponse movementResponse = MovementMapper.fromMovementToMovementResponse(movementService.getById(id));
        return new ResponseEntity<>(movementResponse, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<Page<MovementSummaryResponse>> getMovementsByAccountId(
            @PathVariable Long accountId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        Page<Movement> movementPage = movementService.getAllByAccountId(accountId, page, size);
        List<MovementSummaryResponse> movementSummaries = movementPage.data().stream()
                .map(MovementMapper::fromMovementToMovementSummaryResponse)
                .toList();

        Page<MovementSummaryResponse> responsePage = new Page<>(movementSummaries, page, size, movementPage.totalElements(), movementPage.totalPages());
        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }
}
