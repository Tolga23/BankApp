package com.thardal.bankapp.transactional.service;

import com.thardal.bankapp.customer.entity.Customer;
import com.thardal.bankapp.customer.service.entityservice.CustomerEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NonTransactionalService {

    private final CustomerEntityService customerEntityService;
    public void save() {
        Customer customer = new Customer();
        customer.setName("Test");
        customer.setSurname("text");
        customer.setIdentityNo(453242340L);
        customer.setPassword("pass");

        customerEntityService.save(customer);

        System.out.println("end");
    }
}
