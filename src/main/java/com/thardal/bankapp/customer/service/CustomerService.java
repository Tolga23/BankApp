package com.thardal.bankapp.customer.service;

import com.thardal.bankapp.customer.converter.CustomerConverter;
import com.thardal.bankapp.customer.converter.CustomerMapper;
import com.thardal.bankapp.customer.dto.CustomerDto;
import com.thardal.bankapp.customer.dto.CustomerSaveRequestDto;
import com.thardal.bankapp.customer.dto.CustomerUpdateRequestDto;
import com.thardal.bankapp.customer.entity.Customer;
import com.thardal.bankapp.customer.enums.CustomerErrorMessages;
import com.thardal.bankapp.customer.service.entityservice.CustomerEntityService;
import com.thardal.bankapp.global.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public CustomerDto save(CustomerSaveRequestDto customerSaveRequestDto) {
        Customer customer = CustomerMapper.INSTANCE.convertToCustomerSaveDto(customerSaveRequestDto);
        Customer save = customerEntityService.save(customer);

        CustomerDto customerDto = CustomerMapper.INSTANCE.convertToCustomerDto(save);

        return customerDto;
    }

    public void delete(Long id) {
        Customer customerById = getCustomerByIdWithControl(id);

        customerEntityService.delete(customerById);
    }

    public CustomerDto findById(Long id) {
        Customer customer = getCustomerByIdWithControl(id);

        CustomerDto customerDto = CustomerMapper.INSTANCE.convertToCustomerDto(customer);
        return customerDto;
    }

    public Customer getCustomerByIdWithControl(Long id) {
        Optional<Customer> byIdOptional = customerEntityService.findById(id);

        Customer customer;
        if (byIdOptional.isPresent()) {
            customer = byIdOptional.get();
        } else {
            throw new ItemNotFoundException(CustomerErrorMessages.CUSTOMER_ERROR_MESSAGES);
        }
        return customer;
    }

    public CustomerDto update(CustomerUpdateRequestDto customerUpdateRequestDto) {

        isCustomerExisted(customerUpdateRequestDto);

        Customer customer = CustomerMapper.INSTANCE.covertToCustomerUpdateDto(customerUpdateRequestDto);
        customerEntityService.update(customer);

        CustomerDto customerDto = CustomerMapper.INSTANCE.convertToCustomerDto(customer);

        return customerDto;
    }

    private void isCustomerExisted(CustomerUpdateRequestDto customerUpdateRequestDto) {
        Long id = customerUpdateRequestDto.getId();
        boolean isCustomerExisted = customerEntityService.existsById(id);

        if (!isCustomerExisted) throw new ItemNotFoundException(CustomerErrorMessages.CUSTOMER_ERROR_MESSAGES);
    }
}
