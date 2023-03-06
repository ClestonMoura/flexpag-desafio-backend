package com.flexpag.paymentscheduler.controller;

import com.flexpag.paymentscheduler.dto.PaymentDto;
import com.flexpag.paymentscheduler.dto.PaymentRequestDto;
import com.flexpag.paymentscheduler.entity.enums.PaymentStatus;
import com.flexpag.paymentscheduler.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments(@RequestParam(required = false)PaymentStatus status) {
        List<PaymentDto> paymentDtoList = paymentService.findAllPayments(status);

        if (paymentDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(paymentDtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentDto paymentDto = paymentService.savePayment(paymentRequestDto);
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable("id") Long id) {
        PaymentDto paymentDto = paymentService.findPaymentById(id);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @PutMapping("/date/{id}")
    public ResponseEntity<PaymentDto> updatePaymentDate(@PathVariable("id") Long id,
                                                        @RequestParam("newDate")
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                        LocalDateTime newDate) {
        PaymentDto paymentDto = paymentService.updatePaymentDate(id, newDate);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @PutMapping("/type/{id}")
    public ResponseEntity<PaymentDto> togglePaymentType(@PathVariable("id") Long id) {
        PaymentDto paymentDto = paymentService.togglePaymentType(id);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<PaymentDto> updatePaymentStatus(@PathVariable("id") Long id) {
        PaymentDto paymentDto = paymentService.updatePaymentStatus(id);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePaymentById(@PathVariable("id") Long id) {
        paymentService.deletePaymentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}