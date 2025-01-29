package br.com.tdd.services;

import java.time.LocalDate;
import java.util.Date;

public class Order {

    private String id;
    private String produtcName;
    private Long amount;
    private LocalDate creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdutcName() {
        return produtcName;
    }

    public void setProdutcName(String produtcName) {
        this.produtcName = produtcName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

}
