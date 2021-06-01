package com.web.market.service;

import com.web.market.entity.Order;
import com.web.market.entity.OrderedGood;
import com.web.market.model.User;
import lombok.NonNull;

import java.util.List;

public interface OrderService {

    void add(@NonNull Order order);

    void addOrderedGood(@NonNull OrderedGood orderedGood);

    Order createOrder(Order newOrder);

    List<Order> getAll();

    Order getById(@NonNull Integer id);

    List<OrderedGood> getOrderedGoods();

    List<OrderedGood> getOrderedGoodsByOrder(@NonNull Order order);

    List<OrderedGood> getOrderedGoodsForUser(@NonNull User user);

    void update(@NonNull Order order);

    void deleteByOrder(@NonNull Order order);

    void deleteById(@NonNull Integer id);

    boolean checkOrderExist(@NonNull Order order);
}
