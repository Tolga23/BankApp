package com.thardal.bankapp.card.controller;

import com.thardal.bankapp.card.dto.*;
import com.thardal.bankapp.card.service.CreditCardService;
import com.thardal.bankapp.global.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/credit-card")
@RequiredArgsConstructor
public class CreditCardController {
    private final CreditCardService creditCardService;

    @GetMapping
    public ResponseEntity findAll() {
        List<CreditCardDto> creditCardDto = creditCardService.findAll();

        return ResponseEntity.ok(RestResponse.of(creditCardDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        CreditCardDto creditCardDto = creditCardService.findById(id);

        return ResponseEntity.ok(RestResponse.of(creditCardDto));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CreditCardSaveRequestDto creditCardSaveRequestDto) {
        CreditCardDto creditCardDto = creditCardService.save(creditCardSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(creditCardDto));
    }

    @PatchMapping("/cancel/{cardId}")
    public ResponseEntity cancel(@PathVariable Long cardId) {
        creditCardService.cancel(cardId);

        return ResponseEntity.ok(RestResponse.empty());
    }

    @PostMapping("/spend")
    public ResponseEntity spend(@RequestBody CreditCardSpendDto creditCardSpendDto) {
        CreditCardActivityDto creditCardActivityDto = creditCardService.spend(creditCardSpendDto);

        return ResponseEntity.ok(RestResponse.of(creditCardActivityDto));
    }

    @PostMapping("/refund/{activityId}")
    public ResponseEntity refund(@PathVariable Long activityId) {
        CreditCardActivityDto creditCardActivityDto = creditCardService.refund(activityId);

        return ResponseEntity.ok(RestResponse.of(creditCardActivityDto));
    }

    @PostMapping("/payment")
    public ResponseEntity payment(@RequestBody CreditCardPaymentDto creditCardPaymentDto) {
        CreditCardActivityDto creditCardActivityDto = creditCardService.payment(creditCardPaymentDto);

        return ResponseEntity.ok(RestResponse.of(creditCardActivityDto));
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity findAllActivities(@PathVariable Long id,
                                            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                            Optional<Integer> pageOptional,
                                            Optional<Integer> sizeOptional) {
        List<CreditCardActivityDto> creditCardActivityDtoList = creditCardService.findAllActivities(id, startDate, endDate,
                pageOptional, sizeOptional);

        return ResponseEntity.ok(RestResponse.of(creditCardActivityDtoList));
    }

    @GetMapping("/{id}/statements")
    public ResponseEntity statement(@PathVariable Long id) {
        CreditCardStatementDto creditCardStatementDto = creditCardService.statement(id);

        return ResponseEntity.ok(RestResponse.of(creditCardStatementDto));
    }


}
