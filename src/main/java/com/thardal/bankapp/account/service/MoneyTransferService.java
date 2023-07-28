package com.thardal.bankapp.account.service;

import com.thardal.bankapp.account.converter.AccountMapper;
import com.thardal.bankapp.account.dto.AccountMoneyActivityDto;
import com.thardal.bankapp.account.dto.MoneyTransferDto;
import com.thardal.bankapp.account.dto.MoneyTransferSaveRequestDto;
import com.thardal.bankapp.account.entity.AccountActivity;
import com.thardal.bankapp.account.entity.AccountMoneyTransfer;
import com.thardal.bankapp.account.enums.AccountActivityType;
import com.thardal.bankapp.account.service.entityservice.AccountMoneyTransferEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class MoneyTransferService {
    private final AccountMoneyTransferEntityService moneyTransferEntityService;
    private final AccountActivityService accountActivityService;

    public MoneyTransferDto transferMoney(MoneyTransferSaveRequestDto moneyTransferSaveRequestDto) {
        AccountMoneyTransfer accountMoneyTransfer = AccountMapper.INSTANCE.convertToMoneyTransfer(moneyTransferSaveRequestDto);
        accountMoneyTransfer.setTransactionDate(new Date());

        Long accountIdFrom = accountMoneyTransfer.getAccountIdFrom();
        Long accountIdTo = accountMoneyTransfer.getAccountIdTo();
        BigDecimal amount = accountMoneyTransfer.getAmount();

        // builder pattern to create an AccountMoneyActivityDto object with the given parameters
        AccountMoneyActivityDto accountMoneyActivityDtoOut = AccountMoneyActivityDto.builder()
                .accountId(accountIdFrom)
                .amount(amount)
                .accountActivityType(AccountActivityType.SEND)
                .build();

        accountActivityService.moneyOut(accountMoneyActivityDtoOut);

        AccountMoneyActivityDto accountMoneyActivityDtoOutIn = AccountMoneyActivityDto.builder()
                .accountId(accountIdTo)
                .amount(amount)
                .accountActivityType(AccountActivityType.GET)
                .build();

        accountActivityService.moneyIn(accountMoneyActivityDtoOutIn);

        accountMoneyTransfer = moneyTransferEntityService.save(accountMoneyTransfer);
        MoneyTransferDto moneyTransferDto = AccountMapper.INSTANCE.convertToMoneyTransferDto(accountMoneyTransfer);

        return moneyTransferDto;
    }
}
