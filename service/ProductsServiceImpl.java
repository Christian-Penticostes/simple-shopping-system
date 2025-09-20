package com.jmf.app.java_activity.service;

import com.jmf.app.java_activity.model.ProductsModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductsServiceImpl implements ProductsService {
    private final List<ProductsModel> products = new ArrayList<>(); // store all products in a list
    private int nextId = 1; // auto-increment ID for products

    // constructor
    public ProductsServiceImpl() {

    }

    @Override
    public void addProduct(ProductsModel productsModel) { // assign a unique ID and add product to the list
        productsModel.setId(nextId++);
        products.add(productsModel);
    }

    @Override
    public boolean isProductNameExists(String productName) {
        for (ProductsModel product : products) { // check if product name is already exist
            if (product.getName().equalsIgnoreCase(productName.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeProduct(ProductsModel productsModel) { // remove product by matching its ID
        Iterator<ProductsModel> iterator = products.iterator();
        while (iterator.hasNext()) {
            ProductsModel product = iterator.next();
            if (product.getId().equals(productsModel.getId())) {
                iterator.remove(); // delete product from the list
                return true; // Product found and removed
            }
        }
        return false; // Product not found
    }

    @Override
    public List<ProductsModel> getProducts() { // return all products in the list
        return products;
    }
}
