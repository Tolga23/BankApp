package com.thardal.bankapp.customer.converter;

import com.thardal.bankapp.customer.dto.CustomerDto;
import com.thardal.bankapp.customer.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerConverter {
    public List<CustomerDto> convertToCustomerDtoList(List<Customer> customerList) {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerDto customerDto = convertToCustomerDto(customer);

            customerDtoList.add(customerDto);
        }
        return customerDtoList;
    }

    public CustomerDto convertToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setSurname(customer.getSurname());
        customerDto.setIdentityNo(customer.getIdentityNo());
        return customerDto;
    }
}
