package br.com.tdd.services;

import java.time.LocalDate;
import java.util.UUID;

public class OrderService {

    public Order createOrder(String productName, Long amount) {

        Order order = new Order();

        order.setId(UUID.randomUUID().toString());
        order.setProdutcName(productName);
        order.setAmount(amount);
        order.setCreationDate(LocalDate.now());

        return order;

    }

}
