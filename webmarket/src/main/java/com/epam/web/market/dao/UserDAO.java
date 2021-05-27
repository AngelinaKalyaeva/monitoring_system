package com.epam.web.market.dao;

import com.epam.web.market.model.User;
import lombok.NonNull;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserDAO {

    public User getById(@NonNull Integer id) throws UnsupportedEncodingException;

    public User getByEmail(@NonNull String email);

    public void insert(@NonNull User user);

    public List<User> getAll();

    public List<User> getByGroupId(@NonNull Integer group_id);

    public void update(@NonNull User user);

    public boolean checkUserExistByEmail(@NonNull String email);

    public void deleteById(@NonNull Integer id);

    public void deleteAll();

}
