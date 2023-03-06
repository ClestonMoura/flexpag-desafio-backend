package com.flexpag.paymentscheduler.exception;

import com.flexpag.paymentscheduler.entity.BaseEntity;

/*
    Classe responsável por lidar com errors se uma entidade não for encontrada.
 */

public class EntityNotFoundException extends RuntimeException {

    private Class<? extends BaseEntity> entityClass;

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(Class<? extends BaseEntity> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityNotFoundException(Class<? extends BaseEntity> entityClass, String message) {
        super(message);
        this.entityClass = entityClass;
    }
}
