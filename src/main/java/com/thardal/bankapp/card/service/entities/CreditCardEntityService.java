package com.thardal.bankapp.card.service.entities;

import com.thardal.bankapp.card.dao.CreditCardDao;
import com.thardal.bankapp.card.dto.CreditCardStatementDto;
import com.thardal.bankapp.card.entity.CreditCard;
import com.thardal.bankapp.global.enums.GlobalStatusType;
import com.thardal.bankapp.global.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CreditCardEntityService extends BaseEntityService<CreditCard, CreditCardDao> {
    public CreditCardEntityService(CreditCardDao dao) {
        super(dao);
    }

    public List<CreditCard> findAllByStatusType(GlobalStatusType statusType) {
        return getDao().findAllByStatusType(statusType);
    }

    public List<CreditCard> findAllActiveCreditCardList() {
        return getDao().findAllByStatusType(GlobalStatusType.ACTIVE);
    }

    public CreditCard findByCardNoAndCvvNoAndExpiryDate(Long cardNo, Long cvvNo, Date expiryDate) {
        return getDao().findByCardNoAndCvvNoAndExpiryDateAndStatusType(cardNo, cvvNo, expiryDate, GlobalStatusType.ACTIVE);
    }

    public CreditCardStatementDto getCreditCardDetails(Long creditCardId){
        return getDao().getCreditCardDetails(creditCardId);
    }
}
