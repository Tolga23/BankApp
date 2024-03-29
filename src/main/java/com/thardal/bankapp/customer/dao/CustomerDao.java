package com.thardal.bankapp.customer.dao;

import com.thardal.bankapp.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer,Long> {
Customer findByIdentityNo(Long identityNo);
}
