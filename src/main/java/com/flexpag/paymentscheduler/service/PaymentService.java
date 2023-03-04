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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

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

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updatePaymentStatus() {
        List<Payment> updatedPayments = paymentRepository.findAll();

        if (updatedPayments.isEmpty()) {
            LOGGER.info("No payments updated");
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        updatedPayments.forEach(updatedPayment -> {
            if (updatedPayment.getPayDate().isBefore(now)) {
                updatedPayment.setPaymentStatus(PaymentStatus.PAID);
            }
        });

        paymentRepository.saveAll(updatedPayments);
        updatedPayments.clear();
        LOGGER.info("payments updated");
    }

//    private Payment getPayment(Long id) {
//        return paymentRepository.findById(id)
//                .orElseThrow(() -> new PaymentNotFoundException(id));
//    }

}
