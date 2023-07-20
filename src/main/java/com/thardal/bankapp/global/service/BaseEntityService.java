package com.thardal.bankapp.global.service;

import com.thardal.bankapp.global.enums.GlobalErrorMessages;
import com.thardal.bankapp.global.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseEntityService<E, D extends JpaRepository<E,Long>> {

    private final D dao;

    public List<E> findAll() {
        return dao.findAll();
    }

    public Optional<E> findById(Long id) {
        return dao.findById(id);
    }

    public E save(E entity){
        return (E) dao.save(entity);
    }

    public E update(E entity){
        return dao.save(entity);
    }

    public void delete(E entities) {
        dao.delete(entities);
    }

    public boolean existedById(Long id){
        return dao.existsById(id);
    }

    public E getByIdWithControl(Long id) {
        Optional<E> entityOptional = findById(id);

        E entity;
        if (entityOptional.isPresent()) {
            entity = entityOptional.get();
        } else {
            throw new ItemNotFoundException(GlobalErrorMessages.ITEM_NOT_FOUND);
        }
        return entity;
    }

    public D getDao() {
        return dao;
    }


}