package com.web.market.service;

import com.web.market.entity.Order;
import com.web.market.entity.OrderedGood;
import com.web.market.model.User;
import lombok.NonNull;

import java.util.List;

public interface UserService {
    boolean checkUserExistByEmail(String email);

    User changeMail(User user, String mail);

    User changeName(User user, String name);

    User changePassword(User user, String pwd);

    User changeGroup(User user, Integer group_id);

    User setBlacklisted(User user, Boolean blacklisted);

    User createUser(String name, String email, String pwd, Integer group_id);

    List<Order> getUserOrders(@NonNull User user);

    List<OrderedGood> getUserOrderedGoods(@NonNull User user);

    User getById(@NonNull Integer id);

    User getByEmail(@NonNull String email);

    void add(@NonNull User user);

    List<User> getAll();

    List<User> getByGroupId(@NonNull Integer group_id);

    List<User> getByBlacklisted(@NonNull Boolean blacklisted);

    void update(@NonNull User user);

    void delete(@NonNull User user);

    void deleteById(@NonNull Integer id);

    void deleteAll();

}
