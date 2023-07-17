package com.thardal.bankapp.customer.controller;

import com.thardal.bankapp.customer.dto.CustomerDto;
import com.thardal.bankapp.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity findAll() {
        List<CustomerDto> customerDtos = customerService.findAll();
        return new ResponseEntity(customerDtos, HttpStatus.OK);
    }
}
