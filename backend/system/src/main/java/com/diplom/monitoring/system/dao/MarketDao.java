package com.diplom.monitoring.system.dao;

import com.diplom.monitoring.system.dao.mapper.ProductMapper;
import com.diplom.monitoring.system.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MarketDao {
    private static final String ALL_PRODUCTS_QUERY = "SELECT * FROM production.\"Product\";";

    private final JdbcTemplate postgreSqlJdbcTemplate;

    @Autowired
    public MarketDao(JdbcTemplate postgreSqlJdbcTemplate) {
        this.postgreSqlJdbcTemplate = postgreSqlJdbcTemplate;
    }

    public List<Product> getProductsList() {
        return postgreSqlJdbcTemplate.queryForList(ALL_PRODUCTS_QUERY)
                .stream()
                .map(ProductMapper::mapRow)
                .collect(Collectors.toList());
    }
}
