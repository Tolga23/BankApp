package com.thardal.bankapp.transactional.service;

import com.thardal.bankapp.customer.entity.Customer;
import com.thardal.bankapp.customer.service.entityservice.CustomerEntityService;
import com.thardal.bankapp.transactional.util.TransactionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NonTransactionalService {

    private final CustomerEntityService customerEntityService;
    private TransactionalService transactionalService;

    // setter injection for transactional service to avoid circular dependency problem
    // https://www.baeldung.com/circular-dependencies-in-spring
    @Autowired
    public void setTransactionalService(@Lazy TransactionalService transactionalService) {
        this.transactionalService = transactionalService;
    }

    public void save() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts1");

        customerEntityService.save(customer);

        System.out.println("end");
    }

    public void saveT2N() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts4");

        customerEntityService.save(customer);

        transactionalService.save();

        System.out.println("end");
    }


    public void saveButError() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts7");

        // Non-transactional method but save method is transactional
        // because of transactional save method, this customer will be saved
        // when save method finished, it will commit the transaction
        customerEntityService.save(customer);

        System.out.println("end");

        throw new RuntimeException("error");
    }

    public void saveN2M() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts11");

        customerEntityService.save(customer);

        // active transaction is required for this method, if there is no active transaction, it will throw exception
        transactionalService.saveMandatory();

        System.out.println("end");
    }
}
