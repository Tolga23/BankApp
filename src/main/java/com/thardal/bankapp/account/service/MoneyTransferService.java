package com.thardal.bankapp.account.service;

import com.thardal.bankapp.account.converter.AccountMapper;
import com.thardal.bankapp.account.dto.MoneyTransferDto;
import com.thardal.bankapp.account.dto.MoneyTransferSaveRequestDto;
import com.thardal.bankapp.account.entity.AccountMoneyTransfer;
import com.thardal.bankapp.account.service.entityservice.AccountMoneyTransferEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MoneyTransferService {
    private final AccountMoneyTransferEntityService moneyTransferEntityService;
    private final AccountActivityService accountActivityService;

    public MoneyTransferDto transferMoney(MoneyTransferSaveRequestDto moneyTransferSaveRequestDto) {
        AccountMoneyTransfer accountMoneyTransfer = AccountMapper.INSTANCE.convertToMoneyTransfer(moneyTransferSaveRequestDto);

        Long accountIdFrom = accountMoneyTransfer.getAccountIdFrom();
        Long accountIdTo = accountMoneyTransfer.getAccountIdTo();
        BigDecimal amount = accountMoneyTransfer.getAmount();

        accountActivityService.moneyOut(accountIdFrom, amount);
        accountActivityService.moneyIn(accountIdTo, amount);

        accountMoneyTransfer = moneyTransferEntityService.save(accountMoneyTransfer);
        MoneyTransferDto moneyTransferDto = AccountMapper.INSTANCE.convertToMoneyTransferDto(accountMoneyTransfer);

        return moneyTransferDto;
    }
}
