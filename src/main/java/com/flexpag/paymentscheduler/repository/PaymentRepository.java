package com.flexpag.paymentscheduler.repository;

import com.flexpag.paymentscheduler.entity.Payment;
import com.flexpag.paymentscheduler.entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByPaymentStatusAndPayDateIsLessThanEqual(PaymentStatus status, LocalDateTime dateTime);

}