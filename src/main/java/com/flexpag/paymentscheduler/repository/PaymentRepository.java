package com.flexpag.paymentscheduler.repository;

import com.flexpag.paymentscheduler.entity.Payment;
import com.flexpag.paymentscheduler.entity.enums.PaymentStatus;
import com.flexpag.paymentscheduler.entity.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByPaymentTypeAndPaymentStatusAndPaymentDateIsLessThanEqual(PaymentType type,
                                                                             PaymentStatus status,
                                                                             LocalDateTime dateTime);

    List<Payment> findAllByPaymentStatus(PaymentStatus status);

}