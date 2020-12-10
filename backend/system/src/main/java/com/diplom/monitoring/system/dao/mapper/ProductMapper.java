package com.diplom.monitoring.system.dao.mapper;

import com.diplom.monitoring.system.domain.Product;

import java.util.Map;

public class ProductMapper {
    public static Product mapRow(Map<String, Object> rs) {
        Product product = new Product();
        product.setId((Long) rs.get("id"));
        product.setName((String) rs.get("name"));
        product.setPrice((Long) rs.get("price"));
        product.setCurrency((String) rs.get("currency"));
        product.setDescription((String) rs.get("description"));
        product.setAvailability((Boolean) rs.get("availability"));
        product.setCount((Long) rs.get("count"));
        return product;
    }
}
