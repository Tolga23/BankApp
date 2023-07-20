package com.thardal.bankapp.global.service;

import com.thardal.bankapp.global.entity.BaseAdditionalFields;
import com.thardal.bankapp.global.entity.BaseEntity;
import com.thardal.bankapp.global.enums.GlobalErrorMessages;
import com.thardal.bankapp.global.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseEntityService<E extends BaseEntity, D extends JpaRepository<E, Long>> {

    private final D dao;

    public List<E> findAll() {
        return dao.findAll();
    }

    public Optional<E> findById(Long id) {
        return dao.findById(id);
    }

    public E save(E entity) {
        setAdditionalFields(entity);
        return (E) dao.save(entity);
    }

    public E update(E entity) {
        return dao.save(entity);
    }

    public void delete(E entities) {
        dao.delete(entities);
    }

    public boolean existedById(Long id) {
        return dao.existsById(id);
    }

    private void setAdditionalFields(E entity) {

        BaseAdditionalFields additionalFields = entity.getAdditionalFields();

        Long currentCustomerId = getCurrentCustomerId();

        if (additionalFields == null) {
            additionalFields = new BaseAdditionalFields();
            entity.setAdditionalFields(additionalFields);
        }

        if (entity.getId() == null) {
            additionalFields.setCreatedDate(new Date());
            additionalFields.setCreatedBy(currentCustomerId);
        }

        additionalFields.setUpdatedDate(new Date());
        additionalFields.setUpdatedBy(currentCustomerId);
    }

    private Long getCurrentCustomerId() {
        Long currentCustomer = null;
        return currentCustomer;
    }

    public E getByIdWithControl(Long id) {
        Optional<E> entityOptional = findById(id);

        E entity;
        if (entityOptional.isPresent()) {
            entity = entityOptional.get();
        } else {
            throw new BusinessException(GlobalErrorMessages.ITEM_NOT_FOUND);
        }
        return entity;
    }

    public D getDao() {
        return dao;
    }


}
