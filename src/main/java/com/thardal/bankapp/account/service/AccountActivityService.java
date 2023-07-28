package com.thardal.bankapp.account.service;

import com.thardal.bankapp.account.converter.AccountMapper;
import com.thardal.bankapp.account.dto.AccountActivityDto;
import com.thardal.bankapp.account.dto.AccountMoneyActivityDto;
import com.thardal.bankapp.account.dto.AccountMoneyActivityRequestDto;
import com.thardal.bankapp.account.entity.Account;
import com.thardal.bankapp.account.entity.AccountActivity;
import com.thardal.bankapp.account.enums.AccountActivityType;
import com.thardal.bankapp.account.enums.AccountErrorMessage;
import com.thardal.bankapp.account.service.entityservice.AccountActivityEntityService;
import com.thardal.bankapp.account.service.entityservice.AccountEntityService;
import com.thardal.bankapp.global.enums.GlobalErrorMessages;
import com.thardal.bankapp.global.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountActivityService {
    private final AccountActivityEntityService accountActivityEntityService;
    private final AccountEntityService accountEntityService;

    public AccountActivityDto withdraw(AccountMoneyActivityRequestDto accountMoneyActivityRequestDto) {
        validateAccountMoneyActivityRequestDto(accountMoneyActivityRequestDto);

        Long accountId = accountMoneyActivityRequestDto.getAccountId();
        BigDecimal amount = accountMoneyActivityRequestDto.getAmount();

        // builder pattern to create an AccountMoneyActivityDto object with the given parameters
        AccountMoneyActivityDto moneyActivityDto = AccountMoneyActivityDto.builder()
                .accountId(accountId)
                .amount(amount)
                .accountActivityType(AccountActivityType.WITHDRAW)
                .build();

        AccountActivity accountActivity = moneyOut(moneyActivityDto);

        AccountActivityDto accountActivityDto = AccountMapper.INSTANCE.convertToAccountActivityDto(accountActivity);

        return accountActivityDto;
    }

    public AccountActivity moneyOut(AccountMoneyActivityDto accountMoneyActivityDto) {
        validateMoneyActivityDto(accountMoneyActivityDto);

        Long accountId = accountMoneyActivityDto.getAccountId();
        BigDecimal amount = accountMoneyActivityDto.getAmount();
        AccountActivityType accountActivityType = accountMoneyActivityDto.getAccountActivityType();

        // Get the account by id
        Account account = accountEntityService.getByIdWithControl(accountId);

        // Check if the account has enough balance
        BigDecimal remainingBalance = account.getCurrentBalance().subtract(amount);

        // If the balance is less than 0, throw an exception
        validateBalance(remainingBalance);

        AccountActivity accountActivity = createAccountActivity(accountId, amount, remainingBalance, accountActivityType);

        // Update the account balance with the remaining balance and save it
        updateCurrentBalance(account, remainingBalance);

        return accountActivity;
    }

    public AccountActivity moneyIn(AccountMoneyActivityDto accountMoneyActivityDto) {
        validateMoneyActivityDto(accountMoneyActivityDto);

        Long accountId = accountMoneyActivityDto.getAccountId();
        BigDecimal amount = accountMoneyActivityDto.getAmount();
        AccountActivityType accountActivityType = accountMoneyActivityDto.getAccountActivityType();

        Account account = accountEntityService.getByIdWithControl(accountId);

        BigDecimal newBalance = account.getCurrentBalance().add(amount);

        AccountActivity accountActivity = createAccountActivity(accountId, amount, newBalance, accountActivityType);

        updateCurrentBalance(account, newBalance);

        return accountActivity;
    }

    public AccountActivityDto deposit(AccountMoneyActivityDto accountMoneyActivityDto) {
        validateMoneyActivityDto(accountMoneyActivityDto);

        Long accountId = accountMoneyActivityDto.getAccountId();
        BigDecimal amount = accountMoneyActivityDto.getAmount();

        AccountMoneyActivityDto moneyActivityDto = AccountMoneyActivityDto.builder()
                .accountId(accountId)
                .amount(amount)
                .accountActivityType(AccountActivityType.DEPOSIT)
                .build();

        AccountActivity accountActivity = moneyIn(moneyActivityDto);

        AccountActivityDto accountActivityDto = AccountMapper.INSTANCE.convertToAccountActivityDto(accountActivity);

        return accountActivityDto;
    }

    private static void validateBalance(BigDecimal remainingBalance) {
        if (remainingBalance.compareTo(BigDecimal.ZERO) < 0) throw new BusinessException(AccountErrorMessage.INSUFFICIENT_BALANCE);
    }

    private void updateCurrentBalance(Account account, BigDecimal remainingBalance) {
        account.setCurrentBalance(remainingBalance);
        account = accountEntityService.save(account);
    }

    private AccountActivity createAccountActivity(Long accountId, BigDecimal amount, BigDecimal remainingBalance, AccountActivityType accountActivityType) {
        AccountActivity accountActivity = new AccountActivity();
        accountActivity.setAccountActivityType(accountActivityType);
        accountActivity.setAccountId(accountId);
        accountActivity.setAmount(amount);
        accountActivity.setTransactionDate(new Date());
        accountActivity.setCurrentBalance(remainingBalance);
        accountActivity = accountActivityEntityService.save(accountActivity);
        return accountActivity;
    }

    private void validateMoneyActivityDto(AccountMoneyActivityDto accountMoneyActivityDto) {
        if (accountMoneyActivityDto == null) throw new BusinessException(GlobalErrorMessages.PARAMETER_CAN_NOT_BE_NULL);
    }

    private void validateAccountMoneyActivityRequestDto(AccountMoneyActivityRequestDto accountMoneyActivityRequestDto) {
        if (accountMoneyActivityRequestDto == null) throw new BusinessException(GlobalErrorMessages.PARAMETER_CAN_NOT_BE_NULL);
    }
}
