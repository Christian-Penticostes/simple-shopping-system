package com.jmf.app.java_activity.model;

import java.math.BigDecimal;

public class ProductsModel {
    private Integer id;
    private String name;
    private BigDecimal price;

    // constructor
    public ProductsModel() {

    }

    public ProductsModel(Integer id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductsModel(Integer id) {
        this.id = id;
    }

    // getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
