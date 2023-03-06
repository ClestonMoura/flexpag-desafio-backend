package com.flexpag.paymentscheduler.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
    Classe utilizada pelo usuário para um envio de uma nova entidade.
 */

@JsonIgnoreProperties({"id", "paymentStatus"})
public class PaymentRequestDto extends PaymentDto {
}