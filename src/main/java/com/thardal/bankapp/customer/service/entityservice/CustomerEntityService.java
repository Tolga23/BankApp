package com.thardal.bankapp.customer.service.entityservice;

import com.thardal.bankapp.customer.dao.CustomerDao;
import com.thardal.bankapp.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerEntityService {

    private final CustomerDao customerDao;

    public List<Customer> findAll(){
        return customerDao.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerDao.findById(id);
    }


}
