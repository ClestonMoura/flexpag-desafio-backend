package com.flexpag.paymentscheduler.service;

import com.flexpag.paymentscheduler.dto.PaymentDto;
import com.flexpag.paymentscheduler.entity.Payment;
import com.flexpag.paymentscheduler.entity.PaymentStatus;
import com.flexpag.paymentscheduler.exception.PaymentDeleteException;
import com.flexpag.paymentscheduler.exception.PaymentNotFoundException;
import com.flexpag.paymentscheduler.exception.PaymentNotSavedException;
import com.flexpag.paymentscheduler.exception.PaymentUpdateException;
import com.flexpag.paymentscheduler.mapper.PaymentMapper;
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentDto savePayment(PaymentDto paymentDto) {
        Payment payment = paymentMapper.mapToPayment(paymentDto);
        if (payment.getPayDate().isBefore(LocalDateTime.now())) {
            throw new PaymentNotSavedException("Cannot save payment schedule");
        }
        paymentRepository.save(payment);
        return paymentMapper.mapToPaymentDto(payment);
    }

    public List<PaymentDto> findAllPayments() {
        return paymentRepository.findAll().stream().map(paymentMapper::mapToPaymentDto).collect(Collectors.toList());
    }


    public PaymentDto findPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Not found Payment with id + " + id));
        return paymentMapper.mapToPaymentDto(payment);
    }

    public PaymentDto updatePaymentById(Long id, LocalDateTime newDate) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Not found Payment with id + " + id));
        if (payment.getPaymentStatus() != PaymentStatus.PENDING || newDate.isBefore(LocalDateTime.now())) {
            throw new PaymentUpdateException("Error updating Payment with id = " + id);
        }
        payment.setPayDate(newDate);
        paymentRepository.save(payment);
        return paymentMapper.mapToPaymentDto(payment);
    }

    public void deletePaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Not found Payment with id + " + id));
        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new PaymentDeleteException("Error deleting Payment with id = " + id);
        }
        paymentRepository.deleteById(id);
    }

    @Scheduled(cron = "@hourly")
    @Async
    public void updatePaymentStatus() {
        List<Payment> payments = findAllPayments().stream().map(paymentMapper::mapToPayment)
                .collect(Collectors.toList());
        if (payments.isEmpty()) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        for (Payment payment : payments) {
            if (payment.getPayDate().isAfter(now)) {
                payment.setPaymentStatus(PaymentStatus.PAID);
            }
        }
        paymentRepository.saveAll(payments);
    }

//    private Payment getPayment(Long id) {
//        return paymentRepository.findById(id)
//                .orElseThrow(() -> new PaymentNotFoundException(id));
//    }

}
