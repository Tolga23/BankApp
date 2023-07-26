package com.thardal.bankapp.transactional.util;

import com.thardal.bankapp.customer.entity.Customer;
import org.springframework.util.StringUtils;

public class TransactionalUtil {
    public static Customer getDummyCustomer(String suffix) {

        String testName = "Test";

        if(StringUtils.hasText(suffix)){
            testName = testName + "-" + suffix;
        }

        Customer customer = new Customer();
        customer.setName(testName);
        customer.setSurname(testName);
        customer.setIdentityNo(453242340L);
        customer.setPassword("pass");
        return customer;
    }
}
