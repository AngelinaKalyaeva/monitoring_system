package com.web.market.serviceimpl;

import com.web.market.entity.Order;
import com.web.market.entity.OrderedGood;
import com.web.market.model.Good;
import com.web.market.service.CartService;
import com.web.market.service.GoodService;
import com.web.market.service.MetricsApiService;
import com.web.market.service.OrderService;
import com.mysql.cj.exceptions.WrongArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    public static final ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> CART = new ConcurrentHashMap<>();

    private final OrderService orderService;
    private final GoodService goodService;
    private final MetricsApiService metricsApiService;

    @Override
    public void add(@NonNull Integer userid, @NonNull Integer goodid, @NonNull Integer amount) {
        if (CART.containsKey(userid)) {
            Integer currentAmount;
            if (CART.get(userid).containsKey(goodid)) {
                currentAmount = CART.get(userid).get(goodid);
                currentAmount += amount;
                CART.get(userid).put(goodid, currentAmount);
            } else {
                currentAmount = amount;
            }
            CART.get(userid).put(goodid, currentAmount);
        } else {
            CART.put(userid, new ConcurrentHashMap<>());
            add(userid, goodid, amount);
        }
    }

    @Override
    public void setAmount(@NonNull Integer userid, @NonNull Integer goodid, @NonNull Integer amount) {
        if (CART.containsKey(userid)) {
            if (CART.get(userid).containsKey(goodid)) {
                CART.get(userid).put(goodid, amount);
            } else {
                add(userid, goodid, amount);
            }
        } else {
            add(userid, goodid, amount);
        }
    }

    @Override
    public void remove(@NonNull Integer userid, @NonNull Integer goodid) {
        if (CART.containsKey(userid)) {
            if (CART.get(userid).containsKey(goodid)) {
                CART.get(userid).remove(goodid);
            }
        }
    }

    @Override
    public int buy(@NonNull Integer userid, @NonNull String address) {
        int byedGoodsAmount = 0;
        Order order = orderService.createOrder(new Order(0, userid, address));
        for (Good good : goodService.getAllGoods()) {
            if (CART.get(userid) != null && CART.get(userid).containsKey(good.getId())) {
                Integer amount = CART.get(userid).get(good.getId());
                metricsApiService.sendProductDynamic(good.getId(), amount);
                if (amount > 0) {
                    orderService.addOrderedGood(new OrderedGood(order.getId(), good.getId(), amount, good.getPrice().multiply(BigDecimal.valueOf(amount))));
                    Good byedGood = new Good(good.getId(), good.getName(), good.getPrice(),
                            good.getDescription(), good.getNumber() - amount,
                            good.getImage());
                    byedGoodsAmount += byedGood.getPrice().intValue();
                    goodService.updateGood(byedGood);
                } else {
                    throw new WrongArgumentException("amount is wrong");
                }
            }
        }
        clear(userid);

        return byedGoodsAmount;
    }

    @Override
    public void clear(@NonNull Integer userid) {
        CART.remove(userid);
    }

    @Override
    public List<Integer> getUserCartGoods(@NonNull Integer userid) {
        ArrayList<Integer> goods = new ArrayList<>();
        for (Good good : goodService.getAllGoods()) {
            if (CART.get(userid) != null && CART.get(userid).containsKey(good.getId())) {
                Integer amount = CART.get(userid).get(good.getId());
                if (amount > 0) {
                    goods.add(good.getId());
                }
            }
        }
        return goods;
    }

    @Override
    public ConcurrentHashMap<Integer, Integer> getUserCart(@NonNull Integer userid) {
        return CART.get(userid);
    }

    @Override
    public Integer getAmount(Integer userid) {
        Integer amount = 0;
        List<Integer> cartGoods = getUserCartGoods(userid);
        for (Integer goodid : cartGoods) {
            amount += CART.get(userid).get(goodid);
        }
        return amount;
    }

    @Override
    public Integer getCartCost(Integer userid) {
        Integer cost = 0;
        List<Integer> cartGoods = getUserCartGoods(userid);
        for (Integer goodid : cartGoods) {
            cost += goodService.getGoodById(goodid).getPrice().multiply(BigDecimal.valueOf(CART.get(userid).get(goodid))).intValue();
        }
        return cost;
    }
}
