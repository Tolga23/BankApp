package com.thardal.bankapp.card.converter;

import com.thardal.bankapp.card.dto.CreditCardActivityDto;
import com.thardal.bankapp.card.dto.CreditCardDto;
import com.thardal.bankapp.card.dto.CreditCardSaveRequestDto;
import com.thardal.bankapp.card.dto.CreditCardSpendDto;
import com.thardal.bankapp.card.entity.CreditCard;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditCardMapper {

    CreditCardMapper INSTANCE = Mappers.getMapper(CreditCardMapper.class);

    CreditCard convertToCreditCard(CreditCardDto creditCardDto);

    List<CreditCardDto> convertToCreditCardListDto(List<CreditCard> creditCard);

    CreditCardDto convertToCreditCardDto(CreditCard creditCard);

    CreditCardSaveRequestDto convertToCreditCardSaveRequestDto(CreditCard creditCard);

}
