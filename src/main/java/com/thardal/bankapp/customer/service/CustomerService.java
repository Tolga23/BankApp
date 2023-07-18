package com.thardal.bankapp.customer.service;

import com.thardal.bankapp.customer.converter.CustomerConverter;
import com.thardal.bankapp.customer.dto.CustomerDto;
import com.thardal.bankapp.customer.entity.Customer;
import com.thardal.bankapp.customer.service.entityservice.CustomerEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerEntityService customerEntityService;

    private final CustomerConverter customerConverter;

    public List<CustomerDto> findAll() {
        List<Customer> customerList = customerEntityService.findAll();

        List<CustomerDto> customerDtoList = customerConverter.convertToCustomerDtoList(customerList);

        return customerDtoList;
    }


}
