package com.thardal.bankapp.card.controller;

import com.thardal.bankapp.card.dto.CreditCardDto;
import com.thardal.bankapp.card.dto.CreditCardSaveRequestDto;
import com.thardal.bankapp.card.service.CreditCardService;
import com.thardal.bankapp.global.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit-card")
@RequiredArgsConstructor
public class CreditCardController {
    private final CreditCardService creditCardService;

    @GetMapping
    public ResponseEntity findAll() {
        List<CreditCardDto> creditCardDto = creditCardService.findAll();

        return ResponseEntity.ok(RestResponse.of(creditCardDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        CreditCardDto creditCardDto = creditCardService.findById(id);

        return ResponseEntity.ok(RestResponse.of(creditCardDto));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CreditCardSaveRequestDto creditCardSaveRequestDto) {
        CreditCardDto creditCardDto = creditCardService.save(creditCardSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(creditCardDto));
    }

    @PatchMapping("/cancel/{cardId}")
    public ResponseEntity cancel(@PathVariable Long cardId){
        creditCardService.cancel(cardId);

        return ResponseEntity.ok(RestResponse.empty());
    }


}
