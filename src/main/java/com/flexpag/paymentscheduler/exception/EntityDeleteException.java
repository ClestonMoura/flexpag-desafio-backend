package com.flexpag.paymentscheduler.exception;

import com.flexpag.paymentscheduler.entity.BaseEntity;

/*
    Classe responsável por lidar com errors na remoção de uma entidade.
 */

public class EntityDeleteException extends RuntimeException {

    private Class<? extends BaseEntity> entityClass;

    private static final long serialVersionUID = 1L;

    public EntityDeleteException(Class<? extends BaseEntity> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityDeleteException(Class<? extends BaseEntity> entityClass, String message) {
        super(message);
        this.entityClass = entityClass;
    }
}
