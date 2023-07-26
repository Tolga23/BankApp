package com.thardal.bankapp.transactional.service;

import com.thardal.bankapp.customer.entity.Customer;
import com.thardal.bankapp.customer.service.entityservice.CustomerEntityService;
import com.thardal.bankapp.transactional.util.TransactionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionalService2 {
    private final CustomerEntityService customerEntityService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRequiresNew() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts8-2");

        customerEntityService.save(customer);

        System.out.println("end");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRN(int i) {
        Customer customer = TransactionalUtil.getDummyCustomer("ts10- " + i);

        customerEntityService.save(customer);

        if (i ==7) {
            throw new RuntimeException("error");
        }

        System.out.println("end");
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveMandatory() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts11-t");

        customerEntityService.save(customer);

        System.out.println("end");
    }
}
