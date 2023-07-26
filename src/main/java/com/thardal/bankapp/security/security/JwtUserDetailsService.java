package com.thardal.bankapp.security.security;

import com.thardal.bankapp.customer.entity.Customer;
import com.thardal.bankapp.customer.service.entityservice.CustomerEntityService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final CustomerEntityService customerEntityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Long identityNo = Long.valueOf(username);

        Customer customer = customerEntityService.findByIdentityNo(identityNo);

        return JwtUserDetails.create(customer);
    }

    public UserDetails loadUserByUsername(Long id) {


        Customer customer = customerEntityService.getByIdWithControl(id);

        return JwtUserDetails.create(customer);
    }
}
