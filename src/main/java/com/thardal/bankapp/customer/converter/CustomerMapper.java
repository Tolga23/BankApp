package com.thardal.bankapp.customer.converter;

import com.thardal.bankapp.customer.dto.CustomerDto;
import com.thardal.bankapp.customer.dto.CustomerSaveRequestDto;
import com.thardal.bankapp.customer.dto.CustomerUpdateRequestDto;
import com.thardal.bankapp.customer.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer convertToCustomerSaveDto(CustomerSaveRequestDto customerSaveRequestDto);

    Customer covertToCustomerUpdateDto(CustomerUpdateRequestDto customerUpdateRequestDto);

    List<CustomerDto> convertToCustomerDtoList(List<Customer> customerList);

    CustomerDto convertToCustomerDto(Customer customer);
}
