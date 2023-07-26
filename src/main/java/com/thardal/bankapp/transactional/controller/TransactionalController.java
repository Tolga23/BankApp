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

    //  2: transactional save process
    @PostMapping("/ts2")
    public void ts2(){
        transactionalService.save();
    }

    //  3: transactional to non-transactional save process
    @PostMapping("/ts3")
    public void ts3(){
        transactionalService.saveT2N();
    }

    //  4: non-transactional to transactional save process
    @PostMapping("/ts4")
    public void ts4(){
        nonTransactionalService.saveT2N();
    }

    //  5: transactional to transactional save process
    @PostMapping("/ts5")
    public void ts5(){
        transactionalService.saveT2T();
    }

    //  6: transactional save process with error in the end of the process (rollback)
    @PostMapping("/ts6")
    public void ts6(){
        transactionalService.saveButError();
    }

    //  7: non-transactional save process with error
    @PostMapping("/ts7")
    public void ts7(){
        nonTransactionalService.saveButError();
    }

    // 8: using requires new in same class
    @PostMapping("/ts8")
    public void ts8(){
        transactionalService.saveT2RequiresNew();
    }

    // 9: using requires new in different class
    @PostMapping("/ts9")
    public void ts9(){
        transactionalService.saveT2RequiresNewDifferentBean();
    }

    // 10: using requires new commit with error
    @PostMapping("/ts10")
    public void ts10(){
        transactionalService.saveT2RequiresNewWithError();
    }

    // 11: nonTransactional mandatory transaction
    @PostMapping("/ts11")
    public void ts11(){
        nonTransactionalService.saveN2M();
    }

    // 12: mandatory transaction
    @PostMapping("/ts12")
    public void ts12(){
        transactionalService.saveT2M();
    }


}
