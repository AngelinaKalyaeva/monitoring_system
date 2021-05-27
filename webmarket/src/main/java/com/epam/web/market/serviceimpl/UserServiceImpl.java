package com.epam.web.market.serviceimpl;

import com.epam.web.market.dao.OrderDAO;
import com.epam.web.market.dao.UserDAO;
import com.epam.web.market.entity.Order;
import com.epam.web.market.entity.OrderedGood;
import com.epam.web.market.model.User;
import com.epam.web.market.service.OrderService;
import com.epam.web.market.service.UserService;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userdao;
    private OrderDAO orderdao;
    private OrderService orderService;

    @Autowired
    public UserServiceImpl(UserDAO userdao, OrderDAO orderdao, OrderService orderService) {
        this.orderdao = orderdao;
        this.userdao = userdao;
        this.orderService = orderService;
    }

    @Override
    public List<Order> getUserOrders(@NonNull User user) {
        List<Order> allorders = orderdao.getAll();
        ArrayList<Order> userorders = new ArrayList<>();
        for (Order order : allorders) {
            if (order.getUserId().equals(user.getId())) {
                userorders.add(order);
            }
        }
        return userorders;
    }

    @Override
    public boolean checkUserExistByEmail(String email) {
        return userdao.checkUserExistByEmail(email);
    }

    @Override
    public List<OrderedGood> getUserOrderedGoods(@NonNull User user) {
        List<Order> userorders = getUserOrders(user);
        ArrayList<OrderedGood> userorderedgoods = new ArrayList<>();
        for (Order order: userorders) {
            userorderedgoods.addAll(orderService.getOrderedGoodsByOrder(order));
        }
        return userorderedgoods;
    }

    @Override
    public User changeMail(User user, String mail) {
        if(!mail.equals("") && mail != null) {
            user.setEmail(mail);
        }
        update(user);
        return user;
    }

    @Override
    public User changeName(User user, String name) {
        if(!name.equals("") && name != null) {
            user.setName(name);
        }
        update(user);
        return user;
    }

    @Override
    public User changePassword(User user, String pwd) {
        if(!pwd.equals("") && pwd != null) {
            user.setPassword(pwd);
        }
        update(user);
        return user;
    }

    @Override
    public User changeGroup(User user, Integer group_id) {
        if(group_id != 0 && group_id != null) {
            user.setGroupId(group_id);
        }
        update(user);
        return user;
    }

    @Override
    public User setBlacklisted(User user, Boolean blacklisted) {
        if(blacklisted != null) {
            user.setBlacklisted(blacklisted);
        }
        update(user);
        return user;
    }

    @Override
    public User createUser(String name, String email, String pwd, Integer group_id) {
        add(new User(0, name, pwd, false, group_id, email));
        return getByEmail(email);
    }

    public void deleteUser(User user) {
        delete(user);
    }

    @SneakyThrows
    @Transactional
    public User getById(@NonNull Integer id) {
        return userdao.getById(id);
    }

    @Transactional
    public User getByEmail(@NonNull String email) {
        return userdao.getByEmail(email);
    }

    @Transactional
    public void add(@NonNull User user) {
        userdao.insert(user);
    }

    @Transactional
    public List<User> getAll() {
        return userdao.getAll();
    }

    @Transactional
    public List<User> getByGroupId(@NonNull Integer group_id) {
        return userdao.getByGroupId(group_id);
    }

    @Override
    public List<User> getByBlacklisted(@NonNull Boolean blacklisted) {
        List<User> allusers = getAll();
        List<User> users = new ArrayList<User>();

        for (User user : users) {
            if (user.getBlacklisted() == blacklisted) {
                users.add(user);
            }
        }

        return users;
    }

    @Transactional
    public void update(@NonNull User user) {
        userdao.update(user);
    }

    @Override
    public void delete(@NonNull User user) {
        deleteById(user.getId());
    }

    @Transactional
    public void deleteById(@NonNull Integer id) {
        userdao.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        userdao.deleteAll();
    }

}
