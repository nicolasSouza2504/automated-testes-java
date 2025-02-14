package br.com.tdd.services;

import java.math.BigDecimal;

public class CheckoutService {


    public BigDecimal purchaseProduct(String name, String custommerId) {

        PaymentProcessor paymentProcessor = new PaymentProcessor();

        return paymentProcessor.chargeCustormer();
    }
}
