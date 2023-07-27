package com.thardal.bankapp.security.controller;

import com.thardal.bankapp.customer.dto.CustomerDto;
import com.thardal.bankapp.customer.dto.CustomerSaveRequestDto;
import com.thardal.bankapp.global.dto.RestResponse;
import com.thardal.bankapp.security.dto.SecurityLoginRequestDto;
import com.thardal.bankapp.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody SecurityLoginRequestDto securityLoginRequestDto) {
        String token = authenticationService.login(securityLoginRequestDto);

        return ResponseEntity.ok(RestResponse.of(token));
    }

    @PostMapping("/register")
    public ResponseEntity registerSave(@RequestBody CustomerSaveRequestDto customerSaveRequestDto) {
        CustomerDto customerDto = authenticationService.register(customerSaveRequestDto);


        return ResponseEntity.ok(RestResponse.of(customerDto));
    }
}
