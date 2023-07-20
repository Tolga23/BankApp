package com.thardal.bankapp.account.controller;

import com.thardal.bankapp.account.dto.*;
import com.thardal.bankapp.account.service.AccountActivityService;
import com.thardal.bankapp.account.service.AccountService;
import com.thardal.bankapp.account.service.MoneyTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final MoneyTransferService moneyTransferService;
    private final AccountActivityService accountActivityService;
    @GetMapping
    public ResponseEntity findAll(){
        List<AccountDto> accountDtoList = accountService.findAll();

        return ResponseEntity.ok(accountDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        AccountDto accountDto = accountService.findById(id);

        return ResponseEntity.ok(accountDto);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody AccountSaveRequestDto accountSaveRequestDto) {
        AccountDto accountDto = accountService.save(accountSaveRequestDto);

        return ResponseEntity.ok(accountDto);
    }

    @PatchMapping("/cancel/{accountId}")
    public ResponseEntity cancel(@PathVariable Long accountId){
        accountService.cancel(accountId);

        return ResponseEntity.ok(Void.TYPE);
    }

    @PostMapping("/money-transfer")
    public ResponseEntity transferMoney(@RequestBody MoneyTransferSaveRequestDto moneyTransferSaveRequestDto) {
        MoneyTransferDto moneyTransferDto = moneyTransferService.transferMoney(moneyTransferSaveRequestDto);

        return ResponseEntity.ok(moneyTransferDto);
    }

    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@RequestBody AccountMoneyActivityDto accountMoneyActivityDto){
        AccountActivityDto withdraw = accountActivityService.withdraw(accountMoneyActivityDto);

        return ResponseEntity.ok(withdraw);
    }

    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestBody AccountMoneyActivityDto accountMoneyActivityDto){
        AccountActivityDto deposit = accountActivityService.deposit(accountMoneyActivityDto);

        return ResponseEntity.ok(deposit);
    }


}
