package ru.gb.file.warehouse.stream.api;

import java.math.BigDecimal;

public class Order {

    public final BigDecimal amount;

    public Order(BigDecimal amount) {
        this.amount = amount;
    }
}
