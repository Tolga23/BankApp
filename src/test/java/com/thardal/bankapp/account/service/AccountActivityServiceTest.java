package com.thardal.bankapp.account.service;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountActivityServiceTest {

    @InjectMocks
    @Spy
    private AccountActivityService accAccountActivityService;

    @Mock
    private AccountEntityService accountEntityService;

    @Mock
    private AccountActivityEntityService accountActivityEntityService;


    @Test
    void shouldWithdraw() {
        Long accountId = 1L;
        BigDecimal amount = new BigDecimal(100);
        AccountActivityType activityType = AccountActivityType.WITHDRAW;

        AccountMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccountMoneyActivityRequestDto.class);
        when(accMoneyActivityRequestDto.getAccountId()).thenReturn(accountId);
        when(accMoneyActivityRequestDto.getAmount()).thenReturn(amount);

        AccountActivity accAccountActivity = mock(AccountActivity.class);
        when(accAccountActivity.getCurrentBalance()).thenReturn(amount);

        AccountMoneyActivityDto accMoneyActivityDto = AccountMoneyActivityDto.builder()
                .accountId(accountId)
                .amount(amount)
                .accountActivityType(activityType)
                .build();

        doReturn(accAccountActivity).when(accAccountActivityService).moneyOut(accMoneyActivityDto);

        AccountActivityDto result = accAccountActivityService.withdraw(accMoneyActivityRequestDto);

        assertEquals(amount, result.getCurrentBalance());

        verify(accAccountActivityService).moneyOut(accMoneyActivityDto);
    }

    @Test
    void shouldNotWithdrawWhenParameterIsNull() {
        BusinessException businessException = assertThrows(BusinessException.class,
                () -> accAccountActivityService.withdraw(null));

        assertEquals(GlobalErrorMessages.PARAMETER_CAN_NOT_BE_NULL, businessException.getBaseErrorMessages());
    }

    @Test
    void shouldDeposit() {

        Long accountId = 3L;
        BigDecimal amount = new BigDecimal(25);
        AccountActivityType activityType = AccountActivityType.DEPOSIT;

        AccountMoneyActivityDto activityRequestDto = mock(AccountMoneyActivityDto.class);
        when(activityRequestDto.getAccountId()).thenReturn(accountId);
        when(activityRequestDto.getAmount()).thenReturn(amount);

        AccountActivity accAccountActivity = mock(AccountActivity.class);
        when(accAccountActivity.getAccountId()).thenReturn(accountId);

        AccountMoneyActivityDto accMoneyActivityDto = AccountMoneyActivityDto.builder()
                .accountId(accountId)
                .amount(amount)
                .accountActivityType(activityType)
                .build();

        doReturn(accAccountActivity).when(accAccountActivityService).moneyIn(accMoneyActivityDto);

        AccountActivityDto result = accAccountActivityService.deposit(activityRequestDto);

        assertEquals(accountId, result.getAccountId());

        verify(accAccountActivityService).moneyIn(accMoneyActivityDto);
    }

    @Test
    void shouldNotDepositWhenParameterIsNull() {
        BusinessException genBusinessException = assertThrows(BusinessException.class,
                () -> accAccountActivityService.deposit(null));
        assertEquals(genBusinessException.getBaseErrorMessages(), GlobalErrorMessages.PARAMETER_CAN_NOT_BE_NULL);
    }

    @Test
    void shouldMoneyOut() {
        Long accountId = 4L;
        BigDecimal amount = new BigDecimal(550);
        BigDecimal currentBalance = new BigDecimal(600);
        BigDecimal newBalance = currentBalance.subtract(amount);
        AccountActivityType activityType = AccountActivityType.WITHDRAW;

        Account accAccount = mock(Account.class);
        when(accAccount.getCurrentBalance()).thenReturn(currentBalance);

        AccountActivity accAccountActivity = mock(AccountActivity.class);
        when(accAccountActivity.getCurrentBalance()).thenReturn(newBalance);

        when(accountEntityService.getByIdWithControl(accountId)).thenReturn(accAccount);
        when(accountActivityEntityService.save(any())).thenReturn(accAccountActivity);
        when(accountEntityService.save(accAccount)).thenReturn(accAccount);

        AccountMoneyActivityDto accMoneyActivityDto = AccountMoneyActivityDto.builder()
                .accountId(accountId)
                .amount(amount)
                .accountActivityType(activityType)
                .build();

        AccountActivity result = accAccountActivityService.moneyOut(accMoneyActivityDto);

        assertEquals(result.getCurrentBalance(),newBalance);
    }

    @Test
    void shouldNotMoneyOutWhenParameterIsNull() {
        BusinessException businessException = assertThrows(BusinessException.class,
                () -> accAccountActivityService.moneyOut(null));
        assertEquals(businessException.getBaseErrorMessages(),GlobalErrorMessages.PARAMETER_CAN_NOT_BE_NULL);
    }

    @Test
    void shouldNotMoneyOutWhenBalanceIsInsufficient() {
        Long accountId = 4L;
        BigDecimal amount = new BigDecimal(550);
        BigDecimal currentBalance = new BigDecimal(500);
        BigDecimal newBalance = currentBalance.subtract(amount);
        AccountActivityType activityType = AccountActivityType.WITHDRAW;

        Account accAccount = mock(Account.class);
        when(accAccount.getCurrentBalance()).thenReturn(currentBalance);

        when(accountEntityService.getByIdWithControl(accountId)).thenReturn(accAccount);

        AccountMoneyActivityDto accMoneyActivityDto = AccountMoneyActivityDto.builder()
                .accountId(accountId)
                .amount(amount)
                .accountActivityType(activityType)
                .build();

        BusinessException businessException = assertThrows(BusinessException.class,
                () -> accAccountActivityService.moneyOut(accMoneyActivityDto));

        assertEquals(businessException.getBaseErrorMessages(), AccountErrorMessage.INSUFFICIENT_BALANCE);
    }

    @Test
    void shouldMoneyIn() {

        Long accountIdTo = 3L;
        BigDecimal amount = new BigDecimal(500);
        BigDecimal currentBalance = new BigDecimal(400);
        BigDecimal newBalance = amount.add(currentBalance);

        AccountActivityType activityType = AccountActivityType.DEPOSIT;

        Account accAccount = mock(Account.class);
        when(accAccount.getCurrentBalance()).thenReturn(currentBalance);

        AccountActivity accAccountActivity = mock(AccountActivity.class);
        when(accAccountActivity.getCurrentBalance()).thenReturn(newBalance);

        when(accountEntityService.getByIdWithControl(accountIdTo)).thenReturn(accAccount);
        when(accountActivityEntityService.save(any())).thenReturn(accAccountActivity);
        when(accountEntityService.save(accAccount)).thenReturn(accAccount);

        AccountMoneyActivityDto accMoneyActivityDto = AccountMoneyActivityDto.builder()
                .accountId(accountIdTo)
                .amount(amount)
                .accountActivityType(activityType)
                .build();
        AccountActivity result = accAccountActivityService.moneyIn(accMoneyActivityDto);

        assertEquals(result.getCurrentBalance(),newBalance);
    }

    @Test
    void shouldNotMoneyInWhenParameterIsNull() {
        BusinessException businessException = assertThrows(BusinessException.class,
                () -> accAccountActivityService.moneyIn(null));
        assertEquals(businessException.getBaseErrorMessages(),GlobalErrorMessages.PARAMETER_CAN_NOT_BE_NULL);
    }



}