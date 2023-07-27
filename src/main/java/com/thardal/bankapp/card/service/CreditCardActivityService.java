package com.thardal.bankapp.card.service;

import com.thardal.bankapp.card.service.entities.CreditCardActivityEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreditCardActivityService {
    private final CreditCardActivityEntityService creditCardActivityEntityService;
}
