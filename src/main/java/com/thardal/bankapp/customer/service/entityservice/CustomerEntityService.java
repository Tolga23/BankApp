package com.thardal.bankapp.customer.service.entityservice;

import com.thardal.bankapp.customer.dao.CustomerDao;
import com.thardal.bankapp.customer.entity.Customer;
import com.thardal.bankapp.global.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerEntityService extends BaseEntityService<Customer, CustomerDao> {

    public CustomerEntityService(CustomerDao dao) {
        super(dao);
    }

    public Customer findByIdentityNo(Long identityNo) {
        return getDao().findByIdentityNo(identityNo);
    }
}
