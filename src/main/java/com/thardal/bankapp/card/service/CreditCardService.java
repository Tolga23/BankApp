package com.thardal.bankapp.card.service;

import com.thardal.bankapp.card.converter.CreditCardActivityMapper;
import com.thardal.bankapp.card.converter.CreditCardMapper;
import com.thardal.bankapp.card.dto.*;
import com.thardal.bankapp.card.entity.CreditCard;
import com.thardal.bankapp.card.entity.CreditCardActivity;
import com.thardal.bankapp.card.enums.CreditCardActivityType;
import com.thardal.bankapp.card.enums.CreditCardErrorMessage;
import com.thardal.bankapp.card.service.entities.CreditCardActivityEntityService;
import com.thardal.bankapp.card.service.entities.CreditCardEntityService;
import com.thardal.bankapp.global.enums.GlobalStatusType;
import com.thardal.bankapp.global.exceptions.BusinessException;
import com.thardal.bankapp.global.util.DateUtil;
import com.thardal.bankapp.global.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreditCardService {
    private final CreditCardEntityService creditCardEntityService;
    private final CreditCardActivityEntityService creditCardActivityEntityService;

    public List<CreditCardDto> findAll() {
        List<CreditCard> creditCard = creditCardEntityService.findAllActiveCreditCardList();
        List<CreditCardDto> creditCardDto = CreditCardMapper.INSTANCE.convertToCreditCardListDto(creditCard);
        return creditCardDto;
    }

    public CreditCardDto findById(Long id) {
        CreditCard creditCardById = creditCardEntityService.getByIdWithControl(id);

        CreditCardDto creditCardDto = CreditCardMapper.INSTANCE.convertToCreditCardDto(creditCardById);

        return creditCardDto;
    }

    public CreditCardDto save(CreditCardSaveRequestDto creditCardSaveRequestDto) {
        BigDecimal earning = creditCardSaveRequestDto.getEarning();
        String cutOffDayStr = creditCardSaveRequestDto.getCutOffDay();

        BigDecimal limit = calculateLimit(earning);

        LocalDate cutOffDateLocal = getCutOffDateLocal(cutOffDayStr);
        Date dueDate = getDueDate(cutOffDateLocal);
        Date cutOfDate = DateUtil.convertToDate(cutOffDateLocal);

        CreditCardDto creditCardDto = createCardAndConvertToCreditCardDto(limit, dueDate, cutOfDate);

        return creditCardDto;
    }

    public CreditCardActivityDto spend(CreditCardSpendDto creditCardSpendDto) {

        BigDecimal amount = creditCardSpendDto.getAmount();
        String description = creditCardSpendDto.getDescription();

        // get credit card by card no, cvv no and expire date
        CreditCard creditCard = getCreditCard(creditCardSpendDto);

        // validate credit card is exist and not expired
        validateCreditCard(creditCard);

        // calculate current debt and current available card limit
        BigDecimal currentDept = creditCard.getCurrentDebt().add(amount);
        BigDecimal availableCardLimit = creditCard.getAvailableCardLimit().subtract(amount);

        // validate available card limit
        validateCardLimit(availableCardLimit);

        // update credit card with current debt and available card limit
        creditCard = updateCreditCardForSpend(creditCard, currentDept, availableCardLimit);

        // create credit card activity for spend and convert to credit card activity dto
        CreditCardActivity creditCardActivity = createCreditCardActivity(amount, description, creditCard);
        CreditCardActivityDto creditCardActivityDto = CreditCardActivityMapper.INSTANCE.convertToCreditCardActivityDtoList(creditCardActivity);

        return creditCardActivityDto;
    }

    public CreditCardActivityDto refund(Long activityId) {
        CreditCardActivity oldCreditCardActivity = creditCardActivityEntityService.getByIdWithControl(activityId);
        BigDecimal amount = oldCreditCardActivity.getAmount();

        CreditCard creditCard = updateCreditCardForRefund(oldCreditCardActivity, amount);

        CreditCardActivity creditCardActivity = createCreditCardActivityForRefund(oldCreditCardActivity, amount, creditCard);

        CreditCardActivityDto creditCardActivityDto = CreditCardActivityMapper.INSTANCE.convertToCreditCardActivityDtoList(creditCardActivity);

        return creditCardActivityDto;
    }

    public CreditCardActivityDto payment(CreditCardPaymentDto creditCardPaymentDto) {
        Long creditCardId = creditCardPaymentDto.getCreditCardId();
        BigDecimal amount = creditCardPaymentDto.getAmount();

        CreditCard creditCard = creditCardEntityService.getByIdWithControl(creditCardId);


        addLimitToCreditCard(creditCard, amount);

        CreditCardActivity creditCardActivityForPayment = createCreditCardActivityForPayment(creditCardId, amount);

        CreditCardActivityDto creditCardActivityDto = CreditCardActivityMapper.INSTANCE.convertToCreditCardActivityDtoList(creditCardActivityForPayment);

        return creditCardActivityDto;
    }

    private CreditCardActivity createCreditCardActivityForRefund(CreditCardActivity oldCreditCardActivity, BigDecimal amount, CreditCard creditCard) {
        String description = "REFUND -> " + oldCreditCardActivity.getDescription();

        CreditCardActivity creditCardActivity = new CreditCardActivity();
        creditCardActivity.setCreditCardId(creditCard.getId());
        creditCardActivity.setAmount(amount);
        creditCardActivity.setDescription(description);
        creditCardActivity.setTransactionDate(new Date());
        creditCardActivity.setActivityType(CreditCardActivityType.REFUND);


        creditCardActivity = creditCardActivityEntityService.save(creditCardActivity);
        return creditCardActivity;
    }

    private CreditCard updateCreditCardForRefund(CreditCardActivity oldCreditCardActivity, BigDecimal amount) {
        CreditCard creditCard = creditCardEntityService.getByIdWithControl(oldCreditCardActivity.getCreditCardId());

        creditCard = addLimitToCreditCard(creditCard, amount);
        return creditCard;
    }

    private CreditCardActivity createCreditCardActivityForPayment(Long creditCardId, BigDecimal amount) {

        CreditCardActivity creditCardActivity = new CreditCardActivity();
        creditCardActivity.setCreditCardId(creditCardId);
        creditCardActivity.setAmount(amount);
        creditCardActivity.setDescription("PAYMENT");
        creditCardActivity.setTransactionDate(new Date());
        creditCardActivity.setActivityType(CreditCardActivityType.PAYMENT);


        creditCardActivity = creditCardActivityEntityService.save(creditCardActivity);
        return creditCardActivity;
    }

    private CreditCard addLimitToCreditCard(CreditCard creditCard, BigDecimal amount) {
        BigDecimal currentDebt = creditCard.getCurrentDebt().subtract(amount);
        BigDecimal currentAvailableLimit = creditCard.getAvailableCardLimit().add(amount);

        creditCard.setCurrentDebt(currentDebt);
        creditCard.setAvailableCardLimit(currentAvailableLimit);
        creditCard = creditCardEntityService.save(creditCard);
        return creditCard;
    }

    private CreditCard updateCreditCardForSpend(CreditCard creditCard, BigDecimal currentDept, BigDecimal availableCardLimit) {
        creditCard.setCurrentDebt(currentDept);
        creditCard.setAvailableCardLimit(availableCardLimit);

        creditCard = creditCardEntityService.save(creditCard);

        return creditCard;
    }

    private CreditCard getCreditCard(CreditCardSpendDto creditCardSpendDto) {
        Long cardNo = creditCardSpendDto.getCardNo();
        Long cvvNo = creditCardSpendDto.getCvvNo();
        Date expiryDate = creditCardSpendDto.getExpiryDate();
        CreditCard creditCard = creditCardEntityService.findByCardNoAndCvvNoAndExpiryDate(cardNo, cvvNo, expiryDate);
        return creditCard;
    }

    private void validateCardLimit(BigDecimal availableCardLimit) {
        if (availableCardLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(CreditCardErrorMessage.INSUFFICIENT_CARD_LIMIT);
        }
    }

    private void validateCreditCard(CreditCard creditCard) {
        if (creditCard == null) {
            throw new BusinessException(CreditCardErrorMessage.CREDIT_CARD_NOT_FOUND);
        }

        if (creditCard.getExpiryDate().before(new Date())) {
            throw new BusinessException(CreditCardErrorMessage.CREDIT_CARD_EXPIRED);
        }
    }

    private CreditCardActivity createCreditCardActivity(BigDecimal amount, String description, CreditCard creditCard) {
        CreditCardActivity creditCardActivity = new CreditCardActivity();
        creditCardActivity.setCreditCardId(creditCard.getId());
        creditCardActivity.setAmount(amount);
        creditCardActivity.setDescription(description);
        creditCardActivity.setTransactionDate(new Date());
        creditCardActivity.setActivityType(CreditCardActivityType.SPEND);


        creditCardActivity = creditCardActivityEntityService.save(creditCardActivity);
        return creditCardActivity;
    }

    public void cancel(Long cardId) {
        CreditCard creditCard = creditCardEntityService.getByIdWithControl(cardId);

        creditCard.setStatusType(GlobalStatusType.PASSIVE);
        creditCard.setCancalDate(new Date());

        creditCardEntityService.save(creditCard);
    }

    private CreditCardDto createCardAndConvertToCreditCardDto(BigDecimal limit, Date dueDate, Date cutOfDate) {
        Long currentCustomerId = creditCardActivityEntityService.getCurrentCustomerId();
        CreditCard creditCard = createCreditCard(currentCustomerId, limit, dueDate, cutOfDate);


        CreditCardDto creditCardDto = CreditCardMapper.INSTANCE.convertToCreditCardDto(creditCard);

        return creditCardDto;
    }

    private CreditCard createCreditCard(Long customerId, BigDecimal limit, Date dueDate, Date cutOfDate) {
        Date expireDate = getExpireDate();
        Long cardNo = getCardNo();
        Long cvvNo = getCvvNo();

        CreditCard creditCard = new CreditCard();
        creditCard.setCustomerId(customerId);
        creditCard.setCardNo(cardNo);
        creditCard.setCvvNo(cvvNo);
        creditCard.setTotalLimit(limit);
        creditCard.setCutOffDate(cutOfDate);
        creditCard.setDueDate(dueDate);
        creditCard.setExpiryDate(expireDate);
        creditCard.setAvailableCardLimit(limit);
        creditCard.setMinimumPaymentAmount(BigDecimal.ZERO);
        creditCard.setCurrentDebt(BigDecimal.ZERO);
        creditCard.setAvailableCardLimit(limit);
        creditCard.setStatusType(GlobalStatusType.ACTIVE);

        creditCard = creditCardEntityService.save(creditCardEntityService.save(creditCard));
        return creditCard;
    }

    private BigDecimal calculateLimit(BigDecimal earning) {
        return earning.multiply(BigDecimal.valueOf(3));
    }

    private LocalDate getCutOffDateLocal(String cutOffDayStr) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        Month nextMonth = Month.of(currentMonth).plus(1);
        Integer cutOfDay = getCutOffDay(cutOffDayStr);

        LocalDate cutOffDateLocal = LocalDate.of(currentYear, nextMonth, cutOfDay);
        return cutOffDateLocal;
    }

    private Date getExpireDate() {
        LocalDate expireDateLocal = getExpireDateLocal();
        Date expireDate = DateUtil.convertToDate(expireDateLocal);
        return expireDate;
    }

    private LocalDate getExpireDateLocal() {
        return LocalDate.now().plusYears(3);
    }

    private LocalDate getDueDateLocal(LocalDate cutOffDateLocal) {
        return cutOffDateLocal.plusDays(10);
    }

    private Integer getCutOffDay(String cutOffDayStr) {
        if (!StringUtils.hasText(cutOffDayStr)) {
            cutOffDayStr = "1";
        }

        Integer cutOfDay = Integer.valueOf(cutOffDayStr);
        return cutOfDay;
    }

    private long getCvvNo() {
        return StringUtil.getRandomNumber(3);
    }

    private long getCardNo() {
        return StringUtil.getRandomNumber(16);
    }

    private Date getDueDate(LocalDate cutOffDateLocal) {
        LocalDate dueDateLocal = getDueDateLocal(cutOffDateLocal);
        Date dueDate = DateUtil.convertToDate(dueDateLocal);
        return dueDate;
    }

    public List<CreditCardActivityDto> findAllActivities(Long id, Date startDate, Date endDate) {
        List<CreditCardActivity> creditCardActivityList = creditCardActivityEntityService.findAllByCreditCardIdAndTransactionDateBetween(id, startDate, endDate);
        List<CreditCardActivityDto> creditCardActivityDtoList = CreditCardActivityMapper.INSTANCE.convertToCreditCardActivityDtoList(creditCardActivityList);

        return creditCardActivityDtoList;
    }

    public List<CreditCardActivityDto> findAllActivities(Long id, Date startDate, Date endDate,
                                                         Optional<Integer> pageOptional,
                                                         Optional<Integer> sizeOptional) {

        List<CreditCardActivity> creditCardActivityList = creditCardActivityEntityService
                .findAllByCreditCardIdAndTransactionDateBetween(id, startDate, endDate, pageOptional, sizeOptional);
        List<CreditCardActivityDto> creditCardActivityDtoList = CreditCardActivityMapper.INSTANCE.convertToCreditCardActivityDtoList(creditCardActivityList);

        return creditCardActivityDtoList;
    }

    public CreditCardStatementDto statement(Long id) {
        CreditCard creditCard = creditCardEntityService.getByIdWithControl(id);
        Date termEndDate = creditCard.getCutOffDate();
        Long creditCardId = creditCard.getId();

        LocalDate cutOffDateLocalDate = DateUtil.convertToLocalDate(termEndDate);
        LocalDate termStartDateLocal = cutOffDateLocalDate.minusMonths(1);
        Date termStartDate = DateUtil.convertToDate(termStartDateLocal);

        CreditCardStatementDto creditCardStatementDto = creditCardEntityService.getCreditCardDetails(creditCardId);

        List<CreditCardActivity> creditCardActivityList = creditCardActivityEntityService.
                findAllByCreditCardIdAndTransactionDateBetween(creditCard.getId(), termStartDate, termEndDate);

        List<CreditCardActivityDto> creditCardActivityDtoList = CreditCardActivityMapper.INSTANCE.
                convertToCreditCardActivityDtoList(creditCardActivityList);

        creditCardStatementDto.setCreditCardActivityDtoList(creditCardActivityDtoList);

        return creditCardStatementDto;
    }
}
