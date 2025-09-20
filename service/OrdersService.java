package com.jmf.app.java_activity.service;

import com.jmf.app.java_activity.model.OrdersModel;

import java.util.List;

public interface OrdersService {
    void placeOrder(OrdersModel ordersModel);
    void markOrder(OrdersModel ordersModel);

    List<OrdersModel> getOrders();
}
