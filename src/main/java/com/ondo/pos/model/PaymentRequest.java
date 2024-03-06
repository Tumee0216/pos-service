package com.ondo.pos.model;

public class PaymentRequest {
    private double amount;
    private boolean skipPrint;
    private int paymentType;
    private int defaultQrPayment;
    private String extra;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isSkipPrint() {
        return skipPrint;
    }

    public void setSkipPrint(boolean skipPrint) {
        this.skipPrint = skipPrint;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public int getDefaultQrPayment() {
        return defaultQrPayment;
    }

    public void setDefaultQrPayment(int defaultQrPayment) {
        this.defaultQrPayment = defaultQrPayment;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
