package com.thardal.bankapp.account.service;

import com.thardal.bankapp.account.converter.AccountMapper;
import com.thardal.bankapp.account.dto.AccountActivityDto;
import com.thardal.bankapp.account.dto.AccountMoneyActivityDto;
import com.thardal.bankapp.account.entity.Account;
import com.thardal.bankapp.account.entity.AccountActivity;
import com.thardal.bankapp.account.enums.AccountActivityType;
import com.thardal.bankapp.account.enums.AccountErrorMessage;
import com.thardal.bankapp.account.service.entityservice.AccountActivityEntityService;
import com.thardal.bankapp.account.service.entityservice.AccountEntityService;
import com.thardal.bankapp.global.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccountActivityService {
    private final AccountActivityEntityService accountActivityEntityService;
    private final AccountEntityService accountEntityService;

    public AccountActivityDto withdraw(AccountMoneyActivityDto accountMoneyActivityDto) {

        Long accountId = accountMoneyActivityDto.getAccountId();
        BigDecimal amount = accountMoneyActivityDto.getAmount();

        AccountActivity accountActivity = moneyOut(accountId, amount, AccountActivityType.WITHDRAW);

        AccountActivityDto accountActivityDto = AccountMapper.INSTANCE.convertToAccountActivityDto(accountActivity);

        return accountActivityDto;
    }

    public AccountActivity moneyOut(Long accountId, BigDecimal amount,AccountActivityType accountActivityType) {
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

    public AccountActivity moneyIn(Long accountId, BigDecimal amount,AccountActivityType accountActivityType) {
        Account account = accountEntityService.getByIdWithControl(accountId);

        BigDecimal newBalance = account.getCurrentBalance().add(amount);

        AccountActivity accountActivity = createAccountActivity(accountId, amount, newBalance, accountActivityType);

        updateCurrentBalance(account, newBalance);

        return accountActivity;
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

    public AccountActivityDto deposit(AccountMoneyActivityDto accountMoneyActivityDto) {
        Long accountId = accountMoneyActivityDto.getAccountId();
        BigDecimal amount = accountMoneyActivityDto.getAmount();

        AccountActivity accountActivity = moneyIn(accountId, amount, AccountActivityType.DEPOSIT);

        AccountActivityDto accountActivityDto = AccountMapper.INSTANCE.convertToAccountActivityDto(accountActivity);

        return accountActivityDto;
    }
}
