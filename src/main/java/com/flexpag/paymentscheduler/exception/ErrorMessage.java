package com.flexpag.paymentscheduler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/*
    Classe reponsável por montar uma mensagem de erro que sera usada pelas exceptions.
 */

@Getter
@AllArgsConstructor
public class ErrorMessage {
    private Integer statusCode;
    private LocalDateTime timestamp;
    private String message;
}
