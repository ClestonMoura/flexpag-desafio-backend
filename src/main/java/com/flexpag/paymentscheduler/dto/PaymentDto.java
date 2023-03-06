package com.flexpag.paymentscheduler.dto;

import com.flexpag.paymentscheduler.entity.enums.PaymentStatus;
import com.flexpag.paymentscheduler.entity.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
    Classe usada para as transferências realizadas pelos endpoints. Usada para evitar expor a entitade que
    é armazenada no banco de dados.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private Long id;
    private String title;
    private Double amount;
    private PaymentStatus paymentStatus;
    private PaymentType paymentType;
    private LocalDateTime paymentDate;

}