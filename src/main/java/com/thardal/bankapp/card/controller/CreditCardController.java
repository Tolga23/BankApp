package com.thardal.bankapp.card.controller;

import com.thardal.bankapp.card.dto.CreditCardDto;
import com.thardal.bankapp.card.dto.CreditCardSaveRequestDto;
import com.thardal.bankapp.card.entity.CreditCard;
import com.thardal.bankapp.card.service.CreditCardService;
import com.thardal.bankapp.global.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit-card")
@RequiredArgsConstructor
public class CreditCardController {
    private final CreditCardService creditCardService;

    @PostMapping
    public ResponseEntity save(@RequestBody CreditCardSaveRequestDto creditCardSaveRequestDto) {
        CreditCardDto creditCardDto = creditCardService.save(creditCardSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(creditCardDto));
    }


}
