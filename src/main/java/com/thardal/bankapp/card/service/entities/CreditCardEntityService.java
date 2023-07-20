package com.thardal.bankapp.card.service.entities;

import com.thardal.bankapp.card.dao.CreditCardDao;
import com.thardal.bankapp.card.entity.CreditCard;
import com.thardal.bankapp.global.service.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class CreditCardEntityService extends BaseEntityService<CreditCard, CreditCardDao> {
    public CreditCardEntityService(CreditCardDao dao) {
        super(dao);
    }
}
