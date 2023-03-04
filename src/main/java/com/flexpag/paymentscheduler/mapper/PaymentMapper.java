package com.flexpag.paymentscheduler.mapper;

import com.flexpag.paymentscheduler.dto.PaymentDto;
import com.flexpag.paymentscheduler.entity.Payment;
import com.flexpag.paymentscheduler.entity.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment mapToPayment(PaymentDto paymentDto) {
        return Payment.builder()
                .client(paymentDto.getClient())
                .amount(paymentDto.getAmount())
                .paymentStatus(PaymentStatus.PENDING)
                .payDate(paymentDto.getPayDate())
                .build();
    }

    public PaymentDto mapToPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .client(payment.getClient())
                .amount(payment.getAmount())
                .paymentStatus(payment.getPaymentStatus())
                .payDate(payment.getPayDate())
                .build();
    }

}