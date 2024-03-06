package com.ondo.pos;

import com.ondo.pos.controller.PosController;
import com.ondo.pos.model.PaymentRequest;
import com.ondo.pos.model.PaymentResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class PosControllerTest {

    @Mock
    private PosController.EzConnector ezConnectorMock;

    @InjectMocks
    private PosController posController;

    @Test
    void testProcessPayment() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(100.0);
        paymentRequest.setSkipPrint(false);
        paymentRequest.setPaymentType(PosController.PAYMENT_CARD);
        paymentRequest.setDefaultQrPayment(4);
        paymentRequest.setExtra("extra");

        PaymentResponse mockResponse = new PaymentResponse();
        mockResponse.setSucceed(true);
        mockResponse.setMessage("Payment successful");
        mockResponse.setAmount(100.0);

        Mockito.when(ezConnectorMock.payment(any(), any(), any(), any(), any()))
                .thenReturn("{\"succeed\":true,\"message\":\"Payment successful\",\"amount\":100.0}");

        PaymentResponse response = posController.processPayment(paymentRequest);

        assertEquals(mockResponse, response);
    }
}
