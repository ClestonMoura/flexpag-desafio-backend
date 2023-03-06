package com.flexpag.paymentscheduler.service;

import com.flexpag.paymentscheduler.dto.PaymentDto;
import com.flexpag.paymentscheduler.entity.Payment;
import com.flexpag.paymentscheduler.entity.enums.PaymentStatus;
import com.flexpag.paymentscheduler.entity.enums.PaymentType;
import com.flexpag.paymentscheduler.exception.*;
import com.flexpag.paymentscheduler.mapper.PaymentMapper;
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
    Classe responsável por implementar toda a lógica da aplicação
 */

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    //  Retorna uma lista de pagamentos
    public List<PaymentDto> findAllPayments(PaymentStatus status) {

        if (status != null) {
            return paymentRepository.findAllByPaymentStatus(status).stream().map(paymentMapper::mapToPaymentDto)
                    .collect(Collectors.toList());
        }

        return paymentRepository.findAll().stream().map(paymentMapper::mapToPaymentDto)
                .collect(Collectors.toList());
    }

    //  Salva um pagamento
    public PaymentDto savePayment(PaymentDto paymentDto) {
        Payment payment = paymentMapper.mapToPayment(paymentDto);

        if (payment.getPaymentDate().isBefore(LocalDateTime.now())) {
            throw new EntityNotSavedException(Payment.class, "Invalid paymentDate");
        }

        if (payment.getAmount() <= 0) {
            throw new EntityNotSavedException(Payment.class, "Amount cannot be empty or negative");
        }

        paymentRepository.save(payment);
        return paymentMapper.mapToPaymentDto(payment);
    }

    //  Retorna um pagamento com o respectivo id
    public PaymentDto findPaymentById(Long id) {
        Payment payment = getPayment(id);
        return paymentMapper.mapToPaymentDto(payment);
    }

    //  Atualizada a data de um pagamento
    public PaymentDto updatePaymentDate(Long id, LocalDateTime newDate) {
        Payment payment = getPayment(id);

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new EntityUpdateException(Payment.class, "Cannot update paymentDate of Payment with status PAID");
        }

        if (newDate.isBefore(LocalDateTime.now())) {
            throw new EntityUpdateException(Payment.class, "Invalid paymentDate update");
        }

        payment.setPaymentDate(newDate);
        paymentRepository.save(payment);
        return paymentMapper.mapToPaymentDto(payment);
    }

    //  Muda o tipo de um pagamento
    public PaymentDto togglePaymentType(Long id) {
        Payment payment = getPayment(id);

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new EntityUpdateException(Payment.class, "Cannot updated type of Payment with status PAID");
        }

        payment.setPaymentType(payment.getPaymentType() ==
                PaymentType.MANUAL ? PaymentType.AUTO : PaymentType.MANUAL);

        paymentRepository.save(payment);
        return paymentMapper.mapToPaymentDto(payment);
    }

    // Atualiza o status de um pagamento do tipo MANUAL
    public PaymentDto updatePaymentStatus(Long id) {
        Payment payment = getPayment(id);

        if (payment.getPaymentType() == PaymentType.AUTO) {
            throw new EntityUpdateException(Payment.class,
                    "paymentStatus cannot be updated for Payment with type AUTO");
        }

        if (payment.getPaymentStatus() == PaymentStatus.PAID) {
            throw new EntityUpdateException(Payment.class, "Payment already has status PAID");
        }

        payment.setPaymentStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);
        return paymentMapper.mapToPaymentDto(payment);
    }

    //  Remove um pagamento com status PENDING
    public void deletePaymentById(Long id) {
        Payment payment = getPayment(id);

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new EntityDeleteException(Payment.class, "Cannot remove Payment with status PAID");
        }

        paymentRepository.deleteById(id);
    }


    //  Atualiza o status de um pagamento do tipo AUTO automaticamente em um intervalo de tempo
    //  O método logga quando há a quantidade de pagamentos atualizados
    @Scheduled(fixedRate = 120000)
    @Async
    @Transactional
    public void autoUpdatePaymentStatus() {
        List<Payment> updatedPayments = paymentRepository
                .findByPaymentTypeAndPaymentStatusAndPaymentDateIsLessThanEqual(
                        PaymentType.AUTO,
                        PaymentStatus.PENDING,
                        LocalDateTime.now()
                );

        if (updatedPayments.isEmpty()) {
            LOGGER.info("No payments updated");
            return;
        }

        updatedPayments.forEach(updatedPayment -> updatedPayment.setPaymentStatus(PaymentStatus.PAID));
        LOGGER.info("{} payment(s) updated", updatedPayments.size());
    }

    //  Método de auxílio para coletar um pagamento com o respectivo id
    private Payment getPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Payment.class, "Payment not found with id + " + id));
    }

}
