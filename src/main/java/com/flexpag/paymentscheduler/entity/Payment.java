package com.flexpag.paymentscheduler.entity;

import com.flexpag.paymentscheduler.entity.enums.PaymentStatus;
import com.flexpag.paymentscheduler.entity.enums.PaymentType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/*
    Classe utlilizada como entidade do banco de dados para armazenar um novo pagamento.
 */

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
    @NotBlank
    @Size(min = 5, max = 20)
    private String title;
    @NotBlank
    private Double amount;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @NotNull
    private LocalDateTime paymentDate;

}