package com.epam.web.market.daoimpl;

import com.epam.web.market.dao.UserDAO;
import com.epam.web.market.model.User;
import com.epam.web.market.service.MetricsApiService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbc;
    private final BeanPropertyRowMapper<User> rowmapper = BeanPropertyRowMapper.newInstance(User.class);
    private final MetricsApiService metricsApiService;

    @Override
    public User getById(@NonNull Integer id) {
        String statement = "SELECT `id`, `name`, `password`, `blacklisted`, `group_id`, `email` " +
                "FROM `user` " +
                "WHERE `id` = ?";
        metricsApiService.sendQueryErrorCount(statement);
        User user = jdbc.queryForObject(statement, new Object[]{id}, rowmapper);
        user.setPassword(user.getPassword());
        return user;
    }

    @Override
    public User getByEmail(@NonNull String email) {
        String statement = "SELECT `id`, `name`, `password`, `blacklisted`, `group_id`, `email` " +
                "FROM `user` " +
                "WHERE `email` = ?";
        metricsApiService.sendQueryErrorCount(statement);
        User user = jdbc.queryForObject(statement, new Object[]{email}, rowmapper);
        user.setPassword(user.getPassword());
        return user;
    }

    @Override
    public void insert(@NonNull User user) {
        String statement = "INSERT INTO `user` (`name`, `password`, `blacklisted`, `group_id`, `email`) " +
                "VALUES (?, ?, ?, ?, ?)";
        metricsApiService.sendQueryErrorCount(statement);
        jdbc.update(statement, user.getName(), user.getPassword(), user.getBlacklisted(), user.getGroupId(), user.getEmail());
    }

    @Override
    public List<User> getAll() {
        String statement = "SELECT `id`, `name`, `password`, `blacklisted`, `group_id`, `email` " +
                "FROM `user`";
        metricsApiService.sendQueryErrorCount(statement);
        List<Map<String, Object>> rows = jdbc.queryForList(statement);
        return userRowMap(rows);
    }

    @Override
    public List<User> getByGroupId(@NonNull Integer group_id) {
        String statement = "SELECT `id`, `name`, `password`, `blacklisted`, `group_id`, `email` " +
                "FROM `user` " +
                "WHERE `group_id` = " + group_id;
        metricsApiService.sendQueryErrorCount(statement);
        List<Map<String, Object>> rows = jdbc.queryForList(statement);
        return userRowMap(rows);
    }

    private ArrayList<User> userRowMap(@NonNull List<Map<String, Object>> rows) {
        ArrayList<User> users = new ArrayList<>();
        for (Map row : rows) {
            User user = new User((int) row.get("id"));
            user.setName((String) row.get("name"));
            user.setPassword((String) row.get("password"));
            user.setBlacklisted((boolean) row.get("blacklisted"));
            user.setGroupId((int) row.get("group_id"));
            user.setEmail((String) row.get("email"));
            users.add(user);
        }
        return users;
    }

    @Override
    public void update(@NonNull User user) {
        user.setPassword(user.getPassword());

        StringBuilder sqlbuilder = new StringBuilder(1000);
        sqlbuilder.append("UPDATE `user` SET");

        if (user.getName() != null) {
            sqlbuilder.append(" `name` = '" + user.getName() + "',");
        }

        if (user.getEmail() != null) {
            sqlbuilder.append(" `email` = '" + user.getEmail() + "',");
        }
        if (user.getPassword() != null) {
            sqlbuilder.append(" `password` = '" + user.getPassword() + "',");
        }

        if (user.getBlacklisted() != null) {
            sqlbuilder.append(" `blacklisted` = " + user.getBlacklisted() + ",");
        }
        if (user.getGroupId() != null) {
            sqlbuilder.append(" `group_id` = " + user.getGroupId() + "");
        }
        if (sqlbuilder.charAt(sqlbuilder.length() - 1) == ',') {
            sqlbuilder.deleteCharAt(sqlbuilder.length() - 1);
        }

        sqlbuilder.append(" WHERE id = " + user.getId());
        metricsApiService.sendQueryErrorCount(sqlbuilder.toString());
        jdbc.update(sqlbuilder.toString());
    }

    @Override
    public boolean checkUserExistByEmail(String email) {
        String query = "SELECT COUNT(*) FROM `user` WHERE `email` = ?";
        metricsApiService.sendQueryErrorCount(query);
        Integer number = jdbc.queryForObject(query, Integer.class, email);
        return number != 0;
    }


    @Override
    public void deleteById(@NonNull Integer id) {
        String statement = "DELETE FROM `user` " +
                "WHERE `id` = " + id;
        metricsApiService.sendQueryErrorCount(statement);
        jdbc.update(statement);
    }

    @Override
    public void deleteAll() {
        String statement = "DELETE FROM `user`";
        metricsApiService.sendQueryErrorCount(statement);
        jdbc.update(statement);
    }

}

