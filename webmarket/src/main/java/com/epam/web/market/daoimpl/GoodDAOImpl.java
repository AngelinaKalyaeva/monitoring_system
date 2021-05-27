package com.epam.web.market.daoimpl;

import com.epam.web.market.dao.GoodDAO;
import com.epam.web.market.model.Good;
import com.epam.web.market.service.MetricsApiService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GoodDAOImpl implements GoodDAO {
    private final JdbcTemplate jdbcTemplate;
    private final static RowMapper<Good> rowMapper = new BeanPropertyRowMapper<>(Good.class);
    private final MetricsApiService metricsApiService;

    @Override
    public List<Good> getAllGoods() {
        long start = System.currentTimeMillis();
        String sql = "SELECT `id`, `name`, `price`, `description`, `number` FROM `good`";
        metricsApiService.sendQueryErrorCount(sql);
        metricsApiService.sendSlaDatabaseTime(sql, System.currentTimeMillis() - start);
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Good> getGoodsByPriceRange(int x1, int x2) {
        long start = System.currentTimeMillis();
        String sql = "SELECT `id`, `name`, `price`, `description`, `number` FROM `good` " +
                "WHERE `price`>=? AND `price`<=?";
        metricsApiService.sendQueryErrorCount(sql);
        metricsApiService.sendSlaDatabaseTime(sql, System.currentTimeMillis() - start);
        return this.jdbcTemplate.query(sql, rowMapper, x1, x2);
    }

    @Override
    public Good getGoodById(int id) {
        long start = System.currentTimeMillis();
        String sql = "SELECT `id`, `name`, `price`, `description`, `number` FROM `good` WHERE id=?";
        metricsApiService.sendQueryErrorCount(sql);
        metricsApiService.sendSlaDatabaseTime(sql, System.currentTimeMillis() - start);
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public List<Good> getGoodsByNumber(int number) {
        String sql = "SELECT `id`, `name`, `price`, `description`, `number` FROM `good` WHERE number=?";
        metricsApiService.sendQueryErrorCount(sql);
        return this.jdbcTemplate.query(sql, rowMapper, number);
    }

    @Override
    public void addGood(Good good) {
        String sql = "INSERT INTO `good` (`name`,`price`, `description`, `number` ) VALUES (?, ?, ?, ?)";
        metricsApiService.sendQueryErrorCount(sql);
        jdbcTemplate.update(sql, good.getName(), good.getPrice(), good.getDescription(), good.getNumber());
    }

    @Override
    public void updateGood(Good good) {
        String sql = "UPDATE `good` SET `name`=?, `price`=?, `description`=?, `number`=? WHERE id=?";
        metricsApiService.sendQueryErrorCount(sql);
        jdbcTemplate.update(sql, good.getName(), good.getPrice(), good.getDescription(), good.getNumber(), good.getId());
    }

    @Override
    public void updatePriceById(int id, double price) {
        String sql = "UPDATE `good` SET `price`=? WHERE id=?";
        metricsApiService.sendQueryErrorCount(sql);
        jdbcTemplate.update(sql, price, id);
    }

    @Override
    public void updateNumberById(int id, int number) {
        String sql = "UPDATE `good` SET `number`=? WHERE id=?";
        metricsApiService.sendQueryErrorCount(sql);
        jdbcTemplate.update(sql, number, id);
    }

    @Override
    public void deleteGood(int id) {
        String sql = "DELETE FROM `good` WHERE `id`=?";
        metricsApiService.sendQueryErrorCount(sql);
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean goodExists(String name, String desc) {
        String sql = "SELECT count(*) FROM `good` WHERE `name`=? AND `description`=?";
        metricsApiService.sendQueryErrorCount(sql);
        return jdbcTemplate.queryForObject(sql, Integer.class, name, desc) != 0;
    }
}
