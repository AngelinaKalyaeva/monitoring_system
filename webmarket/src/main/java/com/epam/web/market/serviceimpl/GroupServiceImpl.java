package com.epam.web.market.serviceimpl;

import java.util.List;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.web.market.dao.GroupDAO;
import com.epam.web.market.model.Group;
import com.epam.web.market.model.User;
import com.epam.web.market.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {
    private GroupDAO groupDAO;

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public List<Group> getAllGroups() {
        return groupDAO.getAllGroups();
    }

    @Override
    public Group getGroupWithUser(@NonNull User user) {
        return groupDAO.getGroupByUser(user);
    }

    @Override
    public Group getGroupById(@NonNull Integer id) {
        return groupDAO.getGroupById(id);
    }

    @Override
    public Group getGroupByName(@NonNull String name) {
        return groupDAO.getGroupByName(name);
    }

    @Override
    public void update(@NonNull Group group) {
        groupDAO.update(group);
    }

    @Override
    public void removeGroup(@NonNull Integer id) {
        groupDAO.removeGroup(id);
    }

    @Override
    public void addGroup(@NonNull Group group) {
        groupDAO.addGroup(group);
    }
}
