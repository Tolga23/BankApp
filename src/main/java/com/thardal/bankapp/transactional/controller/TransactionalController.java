package com.thardal.bankapp.transactional.controller;

import com.thardal.bankapp.transactional.service.NonTransactionalService;
import com.thardal.bankapp.transactional.service.TransactionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactional")
public class TransactionalController {

    private final NonTransactionalService nonTransactionalService;
    private final TransactionalService transactionalService;


    //  1: nontransactional save process
    @PostMapping("/ts1")
    public void ts1(){
        nonTransactionalService.save();
    }
}
