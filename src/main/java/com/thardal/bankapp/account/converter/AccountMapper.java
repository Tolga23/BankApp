package com.thardal.bankapp.account.converter;

import com.thardal.bankapp.account.dto.*;
import com.thardal.bankapp.account.entity.Account;
import com.thardal.bankapp.account.entity.AccountActivity;
import com.thardal.bankapp.account.entity.AccountMoneyTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto convertToAccountDto(Account account);

    List<AccountDto> convertToAccountDtoList(List<Account> accountList);

    Account convertToAccountSave(AccountSaveRequestDto accountSaveRequestDto);

    AccountMoneyTransfer convertToMoneyTransfer(MoneyTransferSaveRequestDto moneyTransferSaveRequestDto);

    MoneyTransferDto convertToMoneyTransferDto(AccountMoneyTransfer accountMoneyTransfer);

    AccountActivityDto convertToAccountActivityDto(AccountActivity accountActivity);
}
