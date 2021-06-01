package com.web.market.dao;

import com.web.market.entity.Order;
import com.web.market.entity.OrderedGood;
import lombok.NonNull;

import java.util.List;

public interface OrderDAO {
    void add(@NonNull Order order);

    void addOrderedGood(@NonNull OrderedGood orderedGood);

    List<Order> getAll();

    Order getById(@NonNull Integer id);

    List<OrderedGood> getOrderedGoods();

    boolean checkOrderExist(@NonNull Order order);

    void update(@NonNull Order order);

    void deleteByOrder(@NonNull Order order);

    void deleteById(@NonNull Integer id);
}
