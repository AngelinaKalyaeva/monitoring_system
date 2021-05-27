package com.epam.web.market.daoimpl;

import com.epam.web.market.dao.OrderDAO;
import com.epam.web.market.entity.Order;
import com.epam.web.market.entity.OrderedGood;
import com.epam.web.market.service.MetricsApiService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderDAOImpl implements OrderDAO {
    private final JdbcTemplate template;
    private final MetricsApiService metricsApiService;

    @Override
    public void add(@NonNull Order order) {
        long start = System.currentTimeMillis();
        String query = "INSERT INTO `order` (`user_id`, `address`) VALUES (?, ?)";
        metricsApiService.sendQueryErrorCount(query);
        template.update(query, order.getUserId(), order.getAddress());
        metricsApiService.sendSlaDatabaseTime(query, System.currentTimeMillis() - start);
    }

    @Override
    public void addOrderedGood(OrderedGood orderedGood) {
        String query = "INSERT INTO `ordered_good` (`order_id`,`good_id`, `number`, `price`) VALUES (?, ?, ?, ?)";
        metricsApiService.sendQueryErrorCount(query);
        template.update(query, orderedGood.getOrderId(), orderedGood.getGoodId(), orderedGood.getNumber(), orderedGood.getPrice());
    }

    @Override
    public List<Order> getAll() {
        long start = System.currentTimeMillis();
        String query = "SELECT * FROM `order`";
        metricsApiService.sendQueryErrorCount(query);

        List<Order> orders = new ArrayList<Order>();

        List<Map<String, Object>> rows = template.queryForList(query);
        for (Map row : rows) {
            orders.add(new Order((Integer) row.get("id"), (Integer) row.get("user_id"), (String) row.get("address")));
        }
        metricsApiService.sendSlaDatabaseTime(query, System.currentTimeMillis() - start);
        return orders;
    }

    @Override
    public Order getById(@NonNull Integer id) {
        String query = "SELECT * FROM `order` WHERE `id` = ?";
        metricsApiService.sendQueryErrorCount(query);
        return template.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public List<OrderedGood> getOrderedGoods() {
        String query = "SELECT * FROM `ordered_good`";
        metricsApiService.sendQueryErrorCount(query);

        List<OrderedGood> orders = new ArrayList<OrderedGood>();

        List<Map<String, Object>> rows = template.queryForList(query);
        for (Map<String, Object> row : rows) {
            orders.add(new OrderedGood((Integer) row.get("order_id"), (Integer) row.get("good_id"),
                    (Integer) row.get("number"), (BigDecimal) row.get("price")));
        }

        return orders;
    }

    @Override
    public boolean checkOrderExist(Order order) {
        String query = "SELECT COUNT(*) FROM `order` WHERE `id` = ?";
        metricsApiService.sendQueryErrorCount(query);
        Integer number = template.queryForObject(query, Integer.class, order.getId());
        return number != 0;
    }

    @Override
    public void update(@NonNull Order order) {
        String query = "UPDATE `order` SET `id` = ?, `user_id` = ?, `address` = ? WHERE `id` = ?";
        metricsApiService.sendQueryErrorCount(query);
        template.update(query, order.getId(), order.getUserId(), order.getAddress(), order.getId());
    }

    @Override
    public void deleteByOrder(@NonNull Order order) {
        String query = "DELETE FROM `order` WHERE `id` = ? AND `user_id` = ? AND `address` = ?";
        metricsApiService.sendQueryErrorCount(query);
        template.update(query, order.getId(), order.getUserId(), order.getAddress());
    }

    @Override
    public void deleteById(@NonNull Integer id) {
        String query = "DELETE FROM `order` WHERE `id` = ?";
        metricsApiService.sendQueryErrorCount(query);
        template.update(query, id);
    }
}
