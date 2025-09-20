package com.jmf.app.java_activity.service;

import com.jmf.app.java_activity.model.OrdersModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersServiceImpl implements OrdersService {
    private final List<OrdersModel> orders = new ArrayList<>(); // store all orders in a list

    // constructor
    public OrdersServiceImpl() {

    }

    @Override
    public void placeOrder(OrdersModel ordersModel) {
        orders.add(ordersModel);
    }

    @Override
    public void markOrder(OrdersModel ordersModel) {
        for (OrdersModel order : orders) { // find the order by reference and update its status to DELIVERED
            if (order.getReference().equals(ordersModel.getReference())) {
                order.setStatus("DELIVERED");
                break;
            }
        }
    }

    @Override
    public List<OrdersModel> getOrders() { // return all orders
        return orders;
    }
}
