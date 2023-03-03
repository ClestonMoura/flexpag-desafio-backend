package com.flexpag.paymentscheduler.exception;

public class PaymentNotSavedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PaymentNotSavedException(String message) {
        super(message);
    }

}
