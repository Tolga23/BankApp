package com.thardal.bankapp.security.service;

import com.thardal.bankapp.customer.dto.CustomerDto;
import com.thardal.bankapp.customer.dto.CustomerSaveRequestDto;
import com.thardal.bankapp.customer.entity.Customer;
import com.thardal.bankapp.customer.service.CustomerService;
import com.thardal.bankapp.customer.service.entityservice.CustomerEntityService;
import com.thardal.bankapp.security.dto.SecurityLoginRequestDto;
import com.thardal.bankapp.security.enums.EnumJwtConstants;
import com.thardal.bankapp.security.security.JwtTokenGenerator;
import com.thardal.bankapp.security.security.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final CustomerEntityService customerEntityService;

    public CustomerDto register(CustomerSaveRequestDto customerSaveRequestDto) {
        CustomerDto customerDto = customerService.save(customerSaveRequestDto);

        return customerDto;
    }

    public String login(SecurityLoginRequestDto securityLoginRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                securityLoginRequestDto.getIdentityNo(), securityLoginRequestDto.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtTokenGenerator.generateJwtToken(authenticate);

        String bearer = EnumJwtConstants.BEARER.getValue();

        return bearer + token;

    }

    public Customer getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtUserDetails jwtUserDetails = getCurrentJwtUserDetails(authentication);

        Customer customer = null;
        if (jwtUserDetails != null) {
            customer = customerEntityService.getByIdWithControl(jwtUserDetails.getId());
        }

        return customer;
    }

    public Long getCurrentCustomerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtUserDetails jwtUserDetails = getCurrentJwtUserDetails(authentication);

        Long customerId = null;
        if (jwtUserDetails != null) {
            customerId = jwtUserDetails.getId();
        }

        return customerId;
    }

    private static JwtUserDetails getCurrentJwtUserDetails(Authentication authentication) {
        JwtUserDetails jwtUserDetails = null;
        if (authentication != null && authentication.getPrincipal() instanceof JwtUserDetails) {
            jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        }
        return jwtUserDetails;
    }
}
