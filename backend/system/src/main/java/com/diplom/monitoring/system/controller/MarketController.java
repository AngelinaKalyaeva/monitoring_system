package com.diplom.monitoring.system.controller;

import com.diplom.monitoring.system.dao.MarketDao;
import com.diplom.monitoring.system.domain.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MarketController {
    private final MarketDao marketDao;

    public MarketController(MarketDao marketDao) {
        this.marketDao = marketDao;
    }

    @RequestMapping("/products")
    public List<Product> getProducts() {
        return marketDao.getProductsList();
    }
}
