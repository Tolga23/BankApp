package com.thardal.bankapp.card.converter;

import com.thardal.bankapp.card.dto.CreditCardActivityDto;
import com.thardal.bankapp.card.dto.CreditCardSpendDto;
import com.thardal.bankapp.card.entity.CreditCardActivity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditCardActivityMapper {


    CreditCardActivityMapper INSTANCE = Mappers.getMapper(CreditCardActivityMapper.class);
    CreditCardActivityDto convertToCreditCardActivityDto(CreditCardActivity creditCardActivity);

}
