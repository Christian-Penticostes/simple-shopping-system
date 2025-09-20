package com.jmf.app.java_activity.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrdersModel extends ProductsModel {
    private String date;
    private String reference;
    private Integer quantity;
    private BigDecimal total;
    private String status;

    // constructor
    public OrdersModel(String date, String reference, String name, BigDecimal price, Integer quantity, BigDecimal total, String status) {
        this.date = date;
        this.reference = reference;
        super.setName(name);
        super.setPrice(price);
        this.quantity = quantity;
        this.total = total;
        this.status = status;
    }

    // getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
