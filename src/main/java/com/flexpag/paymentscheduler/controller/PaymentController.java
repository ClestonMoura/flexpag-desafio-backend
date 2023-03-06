package com.flexpag.paymentscheduler.controller;

import com.flexpag.paymentscheduler.dto.PaymentDto;
import com.flexpag.paymentscheduler.dto.PaymentRequestDto;
import com.flexpag.paymentscheduler.entity.enums.PaymentStatus;
import com.flexpag.paymentscheduler.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/*
    Classe responsável pela camada de controle e agrupamento dos principais endpoints da aplicação.
    Cada método é um endpoint que interege com a camada de serviço.
 */

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Get a list of all payments, or with especified paymentType param")
    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments(@RequestParam(required = false)PaymentStatus status) {
        List<PaymentDto> paymentDtoList = paymentService.findAllPayments(status);

        if (paymentDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(paymentDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Create a new payment")
    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentDto paymentDto = paymentService.savePayment(paymentRequestDto);
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a payment its id")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable("id") Long id) {
        PaymentDto paymentDto = paymentService.findPaymentById(id);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @Operation(summary = "Update paymentDate with a valid newDate param")
    @PutMapping("/date/{id}")
    public ResponseEntity<PaymentDto> updatePaymentDate(@PathVariable("id") Long id,
                                                        @RequestParam("newDate")
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                        LocalDateTime newDate) {
        PaymentDto paymentDto = paymentService.updatePaymentDate(id, newDate);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @Operation(summary = "Toggle paymentType between AUTO and MANUAL of a payment with paymentStatus PENDING")
    @PutMapping("/type/{id}")
    public ResponseEntity<PaymentDto> togglePaymentType(@PathVariable("id") Long id) {
        PaymentDto paymentDto = paymentService.togglePaymentType(id);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @Operation(summary = "Update paymentStatus of a payment with paymentType MANUAL")
    @PutMapping("/status/{id}")
    public ResponseEntity<PaymentDto> updatePaymentStatus(@PathVariable("id") Long id) {
        PaymentDto paymentDto = paymentService.updatePaymentStatus(id);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete a payment with paymentType PENDING")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePaymentById(@PathVariable("id") Long id) {
        paymentService.deletePaymentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}