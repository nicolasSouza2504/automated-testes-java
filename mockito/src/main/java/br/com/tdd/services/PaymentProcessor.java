package br.com.tdd.services;

import java.math.BigDecimal;

public class PaymentProcessor {

    private boolean allowForeingCurrencys;
    private String fallbackOption;
    private BigDecimal taxRate;

    public PaymentProcessor(boolean allowForeingCurrencys, String fallbackOption, BigDecimal taxRate) {
        this.allowForeingCurrencys = allowForeingCurrencys;
        this.fallbackOption = fallbackOption;
        this.taxRate = taxRate;
    }

    public PaymentProcessor() {
        this(false, "DEBIT", new BigDecimal(19));
    }

    public PaymentProcessor(String fallbackOption, BigDecimal taxRate) {
        this(false, fallbackOption ,taxRate);
    }

    public BigDecimal chargeCustormer() {
        return new BigDecimal(10);
    }

}
