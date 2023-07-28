package com.thardal.bankapp.customer.service;

import com.thardal.bankapp.customer.converter.CustomerConverter;
import com.thardal.bankapp.customer.dto.CustomerDto;
import com.thardal.bankapp.customer.dto.CustomerSaveRequestDto;
import com.thardal.bankapp.customer.dto.CustomerUpdateRequestDto;
import com.thardal.bankapp.customer.entity.Customer;
import com.thardal.bankapp.customer.service.entityservice.CustomerEntityService;
import com.thardal.bankapp.global.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerEntityService customerEntityService;

    @Mock
    private CustomerConverter customerConverter;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldFindAll() {

        Customer customer = mock(Customer.class);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        CustomerDto customerDto = mock(CustomerDto.class);
        List<CustomerDto> customerDtoList = new ArrayList<>();
        customerDtoList.add(customerDto);

        when(customerEntityService.findAll()).thenReturn(customerList);
        when(customerConverter.convertToCustomerDtoList(customerList)).thenReturn(customerDtoList);

        List<CustomerDto> result = customerService.findAll();

        assertEquals(customerDtoList, result);

    }

    @Test
    void shouldFindAllWithEmptyList() {

        List<Customer> customerList = new ArrayList<>();

        List<CustomerDto> customerDtoList = new ArrayList<>();

        when(customerEntityService.findAll()).thenReturn(customerList);
        when(customerConverter.convertToCustomerDtoList(customerList)).thenReturn(customerDtoList);

        List<CustomerDto> result = customerService.findAll();

        assertEquals(customerDtoList, result);
    }

    @Test
    void shouldFindAllWithNullList() {
        List<Customer> customerList = null;

        when(customerEntityService.findAll()).thenReturn(customerList);
        when(customerConverter.convertToCustomerDtoList(customerList)).thenCallRealMethod();

        assertThrows(NullPointerException.class, () -> customerService.findAll());

    }

    @Test
    void shouldSave() {
        CustomerSaveRequestDto customerSaveRequestDto = mock(CustomerSaveRequestDto.class);
        when(customerSaveRequestDto.getPassword()).thenReturn("111");

        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(1L);

        when(passwordEncoder.encode(anyString())).thenReturn("3333");
        when(customerEntityService.save(any())).thenReturn(customer);

        CustomerDto result = customerService.save(customerSaveRequestDto);

        assertEquals(customer.getId(), result.getId());

    }

    @Test
    void shouldNotSaveWhenParameterIsNull() {
        CustomerSaveRequestDto customerSaveRequestDto = null;

        assertThrows(NullPointerException.class, () -> customerService.save(customerSaveRequestDto));
    }

    @Test
    void shouldDelete() {
        Customer customer = mock(Customer.class);
        when(customerEntityService.getByIdWithControl(anyLong())).thenReturn(customer);

        customerService.delete(customer.getId());

        verify(customerEntityService.getByIdWithControl(anyLong()));
        verify(customerEntityService).delete(any());
    }

    @Test
    void shouldDeleteWhenIdDoesNotExist() {
        when(customerEntityService.getByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> customerService.delete(anyLong()));

        verify(customerEntityService).getByIdWithControl(anyLong());
    }

    @Test
    void shouldFndById() {
        Long id = 1L;

        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(id);
        when(customerEntityService.getByIdWithControl(id)).thenReturn(customer);

        CustomerDto result = customerService.findById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist() {
        Long id = 1L;

        when(customerEntityService.getByIdWithControl(id)).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> customerService.findById(id));

        verify(customerEntityService).getByIdWithControl(id);
    }


    @Test
    void shouldUpdate() {
        Long id = 1L;

        CustomerUpdateRequestDto customerUpdateRequestDto = mock(CustomerUpdateRequestDto.class);
        when(customerUpdateRequestDto.getId()).thenReturn(id);

        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(id);
        when(customerEntityService.getByIdWithControl(id)).thenReturn(customer);

        CustomerDto customerDto = mock(CustomerDto.class);
        when(customerConverter.convertToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.update(customerUpdateRequestDto);

        assertEquals(id, result);
    }

}