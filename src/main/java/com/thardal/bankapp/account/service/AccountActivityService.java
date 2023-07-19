package com.thardal.bankapp.account.service;

import com.thardal.bankapp.account.converter.AccountMapper;
import com.thardal.bankapp.account.dto.AccountActivityDto;
import com.thardal.bankapp.account.dto.WithdrawRequestDto;
import com.thardal.bankapp.account.entity.Account;
import com.thardal.bankapp.account.entity.AccountActivity;
import com.thardal.bankapp.account.enums.AccountActivityType;
import com.thardal.bankapp.account.service.entityservice.AccountActivityEntityService;
import com.thardal.bankapp.account.service.entityservice.AccountEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccountActivityService {
    private final AccountActivityEntityService accountActivityEntityService;
    private final AccountEntityService accountEntityService;

    public AccountActivityDto withdraw(WithdrawRequestDto withdrawRequestDto) {

        Long accountId = withdrawRequestDto.getAccountId();
        BigDecimal amount = withdrawRequestDto.getAmount();

        AccountActivity accountActivity = moneyOut(accountId, amount);

        AccountActivityDto accountActivityDto = AccountMapper.INSTANCE.convertToAccountActivityDto(accountActivity);

        return accountActivityDto;
    }

    public AccountActivity moneyOut(Long accountId, BigDecimal amount) {
        // Get the account by id
        Account account = accountEntityService.getByIdWithControl(accountId);

        // Check if the account has enough balance
        BigDecimal remainingBalance = account.getCurrentBalance().subtract(amount);

        // If the balance is less than 0, throw an exception
        validateBalance(remainingBalance);

        AccountActivity accountActivity = createAccountActivity(accountId, amount, remainingBalance, AccountActivityType.SEND);

        // Update the account balance with the remaining balance and save it
        updateCurrentBalance(account, remainingBalance);

        return accountActivity;
    }

    public AccountActivity moneyIn(Long accountId, BigDecimal amount) {
        Account account = accountEntityService.getByIdWithControl(accountId);

        BigDecimal newBalance = account.getCurrentBalance().add(amount);

        AccountActivity accountActivity = createAccountActivity(accountId, amount, newBalance, AccountActivityType.GET);

        updateCurrentBalance(account, newBalance);

        return accountActivity;
    }

    private static void validateBalance(BigDecimal remainingBalance) {
        if (remainingBalance.compareTo(BigDecimal.ZERO) < 0) throw new RuntimeException("Insufficient Balance!");
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
}
