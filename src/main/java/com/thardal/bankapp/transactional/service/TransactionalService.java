package com.thardal.bankapp.transactional.service;

import com.thardal.bankapp.customer.entity.Customer;
import com.thardal.bankapp.customer.service.entityservice.CustomerEntityService;
import com.thardal.bankapp.transactional.util.TransactionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionalService {

    private final CustomerEntityService customerEntityService;
    private final NonTransactionalService nonTransactionalService;
    private final TransactionalService2 transactionalService2;
    public void save() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts2");

        customerEntityService.save(customer);

        System.out.println("end");
    }

    public void saveT2N() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts3");

        customerEntityService.save(customer);

        nonTransactionalService.save();

        System.out.println("end");
    }

    public void saveT2T() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts5");

        customerEntityService.save(customer);

        save();

        System.out.println("end");
    }

    public void saveButError() {

        Customer customer = TransactionalUtil.getDummyCustomer("ts6");

        customerEntityService.save(customer);

        System.out.println("end");

        throw new RuntimeException("error");

    }

    // https://stackoverflow.com/questions/28480480/propagation-requires-new-does-not-create-a-new-transaction-in-spring-with-jpa
    public void saveT2RequiresNew() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts8-1");

        customerEntityService.save(customer);

        saveRequiresNew();

        System.out.println("end");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRequiresNew() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts8-2");

        customerEntityService.save(customer);

        System.out.println("end");
    }


    public void saveT2RequiresNewDifferentBean() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts9-1");

        customerEntityService.save(customer);

        // Suspending current transaction, creating new transaction and resuming current transaction
        // After this method completed, it will commit the transaction and resume the previous transaction
        transactionalService2.saveRequiresNew();

        System.out.println("end");
    }
    public void saveT2RequiresNewWithError() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts9-1");

        customerEntityService.save(customer);

        for (int i = 0; i < 10; i++) {
            transactionalService2.saveRN(i);
        }

        System.out.println("end");
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveMandatory() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts11-t");

        customerEntityService.save(customer);

        System.out.println("end");
    }

    public void saveT2M() {
        Customer customer = TransactionalUtil.getDummyCustomer("ts12");

        customerEntityService.save(customer);

        // This is transactional method, so it will commit the transaction
        transactionalService2.saveMandatory();

        System.out.println("end");
    }
}
