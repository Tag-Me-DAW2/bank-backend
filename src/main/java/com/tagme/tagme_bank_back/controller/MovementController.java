package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.MovementMapper;
import com.tagme.tagme_bank_back.controller.webModel.response.MovementResponse;
import com.tagme.tagme_bank_back.domain.service.MovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
