package com.jmf.app.java_activity.service;

import com.jmf.app.java_activity.model.ProductsModel;

import java.util.List;

public interface ProductsService {
    void addProduct(ProductsModel productsModel);
    boolean isProductNameExists(String productName);
    boolean removeProduct(ProductsModel productsModel);
    List<ProductsModel> getProducts();
}
