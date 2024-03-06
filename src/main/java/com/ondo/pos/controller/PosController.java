package com.ondo.pos.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ondo.pos.model.PaymentRequest;
import com.ondo.pos.model.PaymentResponse;
import com.ondo.pos.model.RefundRequest;

@CrossOrigin("*")
@RestController
public class PosController {

    private static final Logger logger = Logger.getLogger(PosController.class);

    public static final int PAYMENT_NONE = 0;
    public static final int PAYMENT_CARD = 1;
    public static final int PAYMENT_CASH = 2;
    public static final int PAYMENT_SOCIAL_MOBILE = 3;
    public static final int PAYMENT_SOCIAL_QRCODE = 4;
    public static final int PAYMENT_UPI_QRCODE = 5;
    public static final int PAYMENT_MERCHANT_WALLET = 6;
    public static final int PAYMENT_MONPAY = 7;
    public interface EzConnector extends Library {
        String logon();
        String payment(double amount, boolean skipPrint, int payment, int defaultQrPayment, String extra);
        String refund(String traceno, boolean skipPrint);
        String settlement(boolean skipPrint);
        String version();
        String getIdCard();
    }
    
    @PostMapping("/processPayment")
    public PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest) {
        logger.info("Received payment request: " + paymentRequest);
        EzConnector lib = Native.load("ez-connector", EzConnector.class);
        String responseJson = lib.payment(paymentRequest.getAmount(), paymentRequest.isSkipPrint(),
                paymentRequest.getPaymentType(), paymentRequest.getDefaultQrPayment(), paymentRequest.getExtra());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(responseJson, PaymentResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new PaymentResponse();
        }
    }

    @PostMapping("/refund")
    public PaymentResponse refund(@RequestBody RefundRequest refundRequest) {
        logger.info("Received payment request: " + refundRequest);
        EzConnector lib = Native.load("ez-connector", EzConnector.class);
        String responseJson = lib.refund(refundRequest.getTraceno(), refundRequest.isSkipPrint());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(responseJson, PaymentResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new PaymentResponse();
        }
    }

    @GetMapping("/checkVersion")
    public String checkVersion() {
        EzConnector lib = Native.load("ez-connector", EzConnector.class);
        String version = lib.version();
        logger.info("version: " + version);
        return version;
    }

    @PostMapping("/logon")
    public String logon() {
        EzConnector lib = Native.load("ez-connector", EzConnector.class);
        String logon = lib.logon();
        logger.info("version: " + logon);
        return logon;
    }
}
