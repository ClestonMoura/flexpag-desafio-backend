package com.flexpag.paymentscheduler.controller;

import com.flexpag.paymentscheduler.dto.PaymentDto;
import com.flexpag.paymentscheduler.dto.PaymentRequestDto;
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
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> paymentDtoList = paymentService.findAllPayments();
        if (paymentDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
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

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePaymentById(@PathVariable("id") Long id,
                                                        @RequestParam("newDate")
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                        LocalDateTime newDate) {
        PaymentDto paymentDto = paymentService.updatePaymentById(id, newDate);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePaymentById(@PathVariable("id") Long id) {
        paymentService.deletePaymentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

////    @GetMapping
////    @ResponseStatus(HttpStatus.OK)
////    public List<PaymentDto> getAllPayments() {
////        return paymentService.findAllPayments();
////    }
//    @GetMapping
//    public ResponseEntity<List<PaymentDto>> getAllPayments() {
//        try {
//            List<PaymentDto> paymentDtos = paymentService.findAllPayments();
//            return ResponseEntity.ok(paymentDtos);
//        } catch (Exception ex) {
//            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
////    @GetMapping("/{id}")
////    @ResponseStatus(HttpStatus.OK)
////    public PaymentDto checkPayment(@PathVariable Long id) {
////        return paymentService.checkPayment(id);
////    }
//    @GetMapping("/{id}")
//    public ResponseEntity<PaymentDto> checkPayment(@PathVariable Long id) {
//        try{
//            PaymentDto paymentDto = paymentService.checkPayment(id);
//            return ResponseEntity.ok(paymentDto);
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
////    @PostMapping
////    @ResponseStatus(HttpStatus.CREATED)
////    public PaymentDto createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
////        return paymentService.save(paymentRequestDto);
////    }
//    @PostMapping
//    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
//        try {
//            PaymentDto paymentDto = paymentService.save(paymentRequestDto);
//            return ResponseEntity.ok(paymentDto);
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
//
////    @PutMapping("/{id}")
////    @ResponseStatus(HttpStatus.OK)
////    public PaymentDto updatePayDate(@PathVariable Long id, @RequestParam LocalDateTime localDateTime) {
////        return paymentService.updatePayDate(id, localDateTime);
////    }
//    @PutMapping("/{id}")
//    public ResponseEntity<PaymentDto> updatePayDate(@PathVariable Long id, @RequestParam LocalDateTime newDate) {
//        try {
//            PaymentDto paymentDto = paymentService.updatePayDate(id, newDate);
//            return ResponseEntity.ok(paymentDto);
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
//
////    @DeleteMapping("/{id}")
////    @ResponseStatus(HttpStatus.NO_CONTENT)
////    public void deletePayment(@PathVariable Long id) {
////        paymentService.delete(id);
////    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
//        try {
//            paymentService.delete(id);
//            return ResponseEntity.noContent().build();
//        } catch (Exception ex) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//}