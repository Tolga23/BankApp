package com.thardal.bankapp.card.service.entities;

import com.thardal.bankapp.card.dao.CreditCardActivityDao;
import com.thardal.bankapp.card.entity.CreditCardActivity;
import com.thardal.bankapp.global.entity.BaseEntity;
import com.thardal.bankapp.global.service.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class CreditCardActivityEntityService extends BaseEntityService<CreditCardActivity, CreditCardActivityDao> {
    public CreditCardActivityEntityService(CreditCardActivityDao dao) {
        super(dao);
    }
}
