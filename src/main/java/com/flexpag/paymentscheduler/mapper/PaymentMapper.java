package com.flexpag.paymentscheduler.mapper;

import com.flexpag.paymentscheduler.dto.PaymentDto;
import com.flexpag.paymentscheduler.entity.Payment;
import com.flexpag.paymentscheduler.entity.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment mapToPayment(PaymentDto paymentDto) {
        return Payment.builder()
                .title(paymentDto.getTitle())
                .amount(paymentDto.getAmount())
                .paymentStatus(PaymentStatus.PENDING)
                .paymentType(paymentDto.getPaymentType())
                .paymentDate(paymentDto.getPaymentDate())
                .build();
    }

    public PaymentDto mapToPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .title(payment.getTitle())
                .amount(payment.getAmount())
                .paymentStatus(payment.getPaymentStatus())
                .paymentType(payment.getPaymentType())
                .paymentDate(payment.getPaymentDate())
                .build();
    }

}