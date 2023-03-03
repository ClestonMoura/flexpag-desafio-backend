package com.flexpag.paymentscheduler.service;

import com.flexpag.paymentscheduler.dto.PaymentDto;
import com.flexpag.paymentscheduler.dto.PaymentRequestDto;
import com.flexpag.paymentscheduler.entity.Payment;
import com.flexpag.paymentscheduler.entity.PaymentStatus;
import com.flexpag.paymentscheduler.mapper.PaymentMapper;
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentDto save(PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentMapper.mapToPayment(paymentRequestDto);
        if (payment.getPayDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Unable to schedule payment. Verify the pay date.");
        }
        paymentRepository.save(payment);
        return paymentMapper.mapToPaymentDto(payment);
    }

    public List<PaymentDto> findAllPayments() {
        return paymentRepository.findAll().stream().map(paymentMapper::mapToPaymentDto).collect(Collectors.toList());
    }


    public PaymentDto checkPayment(Long id) {
        return paymentMapper.mapToPaymentDto(getPayment(id));
    }

    public PaymentDto updatePayDate(Long id, LocalDateTime newDateTime) {
        Payment payment = getPayment(id);
        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new RuntimeException("Cannot updated PAID payment");
        }
        payment.setPayDate(newDateTime);
        paymentRepository.save(payment);
        return paymentMapper.mapToPaymentDto(payment);
    }

    public void delete(Long id) {
        if (getPayment(id).getPaymentStatus() != PaymentStatus.PENDING) {
            throw new RuntimeException("Cannot delete PAID payment");
        }
        paymentRepository.deleteById(id);
    }

    public void updatePaymentStatus(List<Payment> payments) {
        List<Payment> updatedPayments = new ArrayList<>();
        for (Payment payment : payments) {
            payment.setPaymentStatus(PaymentStatus.PAID);
            updatedPayments.add(payment);
        }
        paymentRepository.saveAll(updatedPayments);
    }

    private Payment getPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find payment with Id " + id));
    }

}
