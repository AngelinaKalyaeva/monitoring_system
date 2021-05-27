package com.epam.web.market.daoimpl;

import java.util.List;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.epam.web.market.dao.GroupDAO;
import com.epam.web.market.model.Group;
import com.epam.web.market.model.User;
import org.springframework.stereotype.Component;

@Component
public class GroupDAOImpl implements GroupDAO {
    private JdbcTemplate jdbcTemplate;
    private BeanPropertyRowMapper<Group> rowmapper = BeanPropertyRowMapper.newInstance(Group.class);

    @Autowired
    public GroupDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Group> getAllGroups() {
        return jdbcTemplate.query("SELECT * FROM `group`", rowmapper);
    }

    @Override
    public Group getGroupByUser(@NonNull User user) {
        return jdbcTemplate.queryForObject(
                "SELECT DISTINCT * FROM `group` WHERE `id` = (SELECT `group_id` FROM `user` WHERE `id` = ?)", rowmapper,
                user.getId());
    }

    @Override
    public Group getGroupById(@NonNull Integer id) {
        return jdbcTemplate.queryForObject("SELECT DISTINCT * FROM `group` WHERE `id` = ?", rowmapper, id);
    }

    @Override
    public Group getGroupByName(@NonNull String name) {
        return jdbcTemplate.queryForObject("SELECT DISTINCT * FROM `group` WHERE `name` = ?", rowmapper, name);
    }

    @Override
    public void update(@NonNull Group group) {
        StringBuilder sqlbuilder = new StringBuilder(1000);
        sqlbuilder.append("UPDATE `order` SET");

        if (group.getName() != null) {
            sqlbuilder.append(" `name` = '" + group.getName() + "',");
        }

        sqlbuilder.append(" WHERE id = " + group.getId());
        jdbcTemplate.update(sqlbuilder.toString());
    }

    @Override
    public void removeGroup(@NonNull Integer id) {
        jdbcTemplate.update("DELETE FROM `group` WHERE `id` = ?", id);
    }

    @Override
    public void addGroup(@NonNull Group group) {
        jdbcTemplate.update("INSERT INTO `group` (`name`) VALUES (?)", group.getName());
    }
}
