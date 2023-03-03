package com.flexpag.paymentscheduler.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"id", "paymentStatus"})
public class PaymentRequestDto extends PaymentDto {
}