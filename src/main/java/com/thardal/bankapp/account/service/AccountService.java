package com.thardal.bankapp.account.service;

import com.thardal.bankapp.account.converter.AccountMapper;
import com.thardal.bankapp.account.dto.AccountDto;
import com.thardal.bankapp.account.dto.AccountSaveRequestDto;
import com.thardal.bankapp.account.entity.Account;
import com.thardal.bankapp.account.service.entityservice.AccountEntityService;
import com.thardal.bankapp.global.enums.GlobalStatusType;
import com.thardal.bankapp.global.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountEntityService accountEntityService;


    public List<AccountDto> findAll() {
        List<Account> accountList = accountEntityService.findAllActiveAccountList(GlobalStatusType.ACTIVE);

        List<AccountDto> accountDtoList = AccountMapper.INSTANCE.convertToAccountDtoList(accountList);

        return accountDtoList;
    }

    public AccountDto findById(Long id) {
        Account account = accountEntityService.getByIdWithControl(id);

        AccountDto accountDto = AccountMapper.INSTANCE.convertToAccountDto(account);

        return accountDto;
    }

    public AccountDto save(AccountSaveRequestDto accountSaveRequestDto) {
        String ibanNo = getIbanNo();

        Long currentCustomerId = accountEntityService.getCurrentCustomerId();

        Account account = AccountMapper.INSTANCE.convertToAccountSave(accountSaveRequestDto);
        account.setStatusType(GlobalStatusType.ACTIVE);
        account.setIbanNo(ibanNo);
        account.setCustomerId(currentCustomerId);

        account = accountEntityService.save(account);

        AccountDto accountDto = AccountMapper.INSTANCE.convertToAccountDto(account);

        return accountDto;
    }

    public void cancel(Long accountId) {
        Account account = accountEntityService.getByIdWithControl(accountId);
        account.setStatusType(GlobalStatusType.PASSIVE);
        account.setCancalDate(new Date());

        accountEntityService.save(account);

    }

    private String getIbanNo() {
        String ibanNo = StringUtil.getRandomNumberAsString(26);
        return "TR" + ibanNo;
    }
}
