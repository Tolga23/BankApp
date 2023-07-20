package com.thardal.bankapp.card.service;

import com.thardal.bankapp.card.service.entities.CreditCardActivityEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardActivityService {
    private final CreditCardActivityEntityService creditCardActivityEntityService;
}
