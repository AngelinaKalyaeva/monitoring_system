package com.epam.web.market.serviceimpl;

import com.epam.web.market.dao.OrderDAO;
import com.epam.web.market.entity.Order;
import com.epam.web.market.entity.OrderedGood;
import com.epam.web.market.model.User;
import com.epam.web.market.service.OrderService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void add(@NonNull Order order) {
        orderDAO.add(order);
    }

    @Override
    public void addOrderedGood(@NonNull OrderedGood orderedGood) {
        orderDAO.addOrderedGood(orderedGood);
    }

    @Override
    public Order createOrder(Order newOrder) {
        add(newOrder);
        List<Order> allorders = getAll();
        Integer addedOrderId = 0;
        for (Order order: allorders) {
            if (order.getUserId().equals(newOrder.getUserId())) {
                if (order.getId() > addedOrderId) {
                    addedOrderId = order.getId();
                }
            }
        }
        return getById(addedOrderId);
    }

    @Override
    public List<Order> getAll() {
        return orderDAO.getAll();
    }

    @Override
    public Order getById(@NonNull Integer id) {
        return orderDAO.getById(id);
    }

    @Override
    public List<OrderedGood> getOrderedGoods() {
        return orderDAO.getOrderedGoods();
    }

    @Override
    public List<OrderedGood> getOrderedGoodsByOrder(@NonNull Order order) {
        List<OrderedGood> orderedGoodsByOrder = new ArrayList<>();

        List<OrderedGood> allOrderedGoods = orderDAO.getOrderedGoods();
        for (OrderedGood orderedGood : allOrderedGoods) {
            if (orderedGood.getOrderId().equals(order.getId())) {
                orderedGoodsByOrder.add(orderedGood);
            }
        }

        return orderedGoodsByOrder;
    }

    @Override
    public List<OrderedGood> getOrderedGoodsForUser(@NonNull User user) {
        List<OrderedGood> orderedGoodsForUser = new ArrayList<>();

        List<OrderedGood> allOrderedGoods = orderDAO.getOrderedGoods();
        List<Order> all = orderDAO.getAll();
        for (OrderedGood orderedGood : allOrderedGoods) {
            for (Order order : all) {
                if (order.getId().equals(orderedGood.getOrderId()) && order.getUserId().equals(user.getId())) {
                    orderedGoodsForUser.add(orderedGood);
                }
            }
        }

        return orderedGoodsForUser;
    }

    @Override
    public void update(@NonNull Order order) {
        orderDAO.update(order);
    }

    @Override
    public void deleteByOrder(@NonNull Order order) {
        orderDAO.deleteByOrder(order);
    }

    @Override
    public void deleteById(@NonNull Integer id) {
        orderDAO.deleteById(id);
    }

    @Override
    public boolean checkOrderExist(Order order) {
        return orderDAO.checkOrderExist(order);
    }
}
