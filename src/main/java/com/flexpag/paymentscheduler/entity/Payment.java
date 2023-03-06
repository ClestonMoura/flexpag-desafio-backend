package com.flexpag.paymentscheduler.entity;

import com.flexpag.paymentscheduler.entity.enums.PaymentStatus;
import com.flexpag.paymentscheduler.entity.enums.PaymentType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private LocalDateTime paymentDate;

}