package com.web.market.service;

import com.web.market.model.Good;

import java.util.List;

public interface GoodService {
    List<Good> getAllGoods();

    List<Good> getGoodsByPriceRange(int x1, int x2);

    Good getGoodById(int id);

    List<Good> getGoodsByNumber(int number);

    boolean addGood(Good good);

    void updateGood(Good good);

    void updatePriceById(int id, double price);

    void updateNumberById(int id, int number);

    void deleteGood(int id);
}
