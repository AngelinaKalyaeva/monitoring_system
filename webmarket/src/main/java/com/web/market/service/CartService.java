package com.web.market.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface CartService {

    void add(Integer userid, Integer goodid, Integer amount);

    void setAmount(Integer userid, Integer goodid, Integer amount);

    void remove(Integer userid, Integer goodid);

    int buy(Integer userid, String address);

    void clear(Integer userid);

    ConcurrentHashMap<Integer, Integer> getUserCart(Integer userid);

    List<Integer> getUserCartGoods(Integer userid);

    Integer getAmount(Integer userid);

    Integer getCartCost(Integer userid);
}
