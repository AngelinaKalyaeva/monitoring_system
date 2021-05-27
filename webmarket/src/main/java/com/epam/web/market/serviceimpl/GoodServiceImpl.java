package com.epam.web.market.serviceimpl;

import com.epam.web.market.dao.GoodDAO;
import com.epam.web.market.model.Good;
import com.epam.web.market.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodDAO goodDAO;

    @Override
    public List<Good> getAllGoods() {
        return goodDAO.getAllGoods();
    }

    @Override
    public List<Good> getGoodsByPriceRange(int x1, int x2) {
        return goodDAO.getGoodsByPriceRange(x1, x2);
    }

    @Override
    public Good getGoodById(int id) {
        return goodDAO.getGoodById(id);
    }

    @Override
    public List<Good> getGoodsByNumber(int number) {
        return goodDAO.getGoodsByNumber(number);
    }

    @Override
    public boolean addGood(Good good) {
        if (goodDAO.goodExists(good.getName(), good.getDescription()))
            return false;
        else {
            goodDAO.addGood(good);
            return true;
        }
    }

    @Override
    public void updateGood(Good good) {
        goodDAO.updateGood(good);
    }

    @Override
    public void updatePriceById(int id, double price) {
        goodDAO.updatePriceById(id, price);
    }

    @Override
    public void updateNumberById(int id, int number) {
        goodDAO.updateNumberById(id, number);
    }

    @Override
    public void deleteGood(int id) {
        goodDAO.deleteGood(id);
    }
}
