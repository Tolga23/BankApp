package com.thardal.bankapp.customer.service;

import com.thardal.bankapp.customer.converter.CustomerConverter;
import com.thardal.bankapp.customer.converter.CustomerMapper;
import com.thardal.bankapp.customer.dto.CustomerDto;
import com.thardal.bankapp.customer.dto.CustomerSaveRequestDto;
import com.thardal.bankapp.customer.dto.CustomerUpdateRequestDto;
import com.thardal.bankapp.customer.entity.Customer;
import com.thardal.bankapp.customer.enums.CustomerErrorMessages;
import com.thardal.bankapp.customer.service.entityservice.CustomerEntityService;
import com.thardal.bankapp.global.exceptions.BusinessException;
import com.thardal.bankapp.global.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerEntityService customerEntityService;

    private final CustomerConverter customerConverter;
    private final PasswordEncoder passwordEncoder;

    public List<CustomerDto> findAll() {
        List<Customer> customerList = customerEntityService.findAll();

        List<CustomerDto> customerDtoList = customerConverter.convertToCustomerDtoList(customerList);

        return customerDtoList;
    }

    public CustomerDto save(CustomerSaveRequestDto customerSaveRequestDto) {
        Customer customer = CustomerMapper.INSTANCE.convertToCustomerSaveDto(customerSaveRequestDto);

        String encodePassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodePassword);

        Customer save = customerEntityService.save(customer);

        CustomerDto customerDto = CustomerMapper.INSTANCE.convertToCustomerDto(save);

        return customerDto;
    }

    public void delete(Long id) {
        Customer customerById = customerEntityService.getByIdWithControl(id);

        customerEntityService.delete(customerById);
    }

    public CustomerDto findById(Long id) {
        Customer customer = customerEntityService.getByIdWithControl(id);

        CustomerDto customerDto = CustomerMapper.INSTANCE.convertToCustomerDto(customer);
        return customerDto;
    }


    public CustomerDto update(CustomerUpdateRequestDto customerUpdateRequestDto) {

        isCustomerExisted(customerUpdateRequestDto);

        Customer customer = CustomerMapper.INSTANCE.covertToCustomerUpdateDto(customerUpdateRequestDto);
        String encodePassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodePassword);
        customerEntityService.update(customer);

        CustomerDto customerDto = CustomerMapper.INSTANCE.convertToCustomerDto(customer);

        return customerDto;
    }

    private void isCustomerExisted(CustomerUpdateRequestDto customerUpdateRequestDto) {
        Long id = customerUpdateRequestDto.getId();
        boolean isCustomerExisted = customerEntityService.existedById(id);

        if (!isCustomerExisted) throw new ItemNotFoundException(CustomerErrorMessages.CUSTOMER_ERROR_MESSAGES);
    }
}
