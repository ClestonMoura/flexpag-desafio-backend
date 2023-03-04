package com.flexpag.paymentscheduler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorMessage {
    private Integer statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;
}
