package com.web.market.serviceimpl;

import java.util.List;

import com.web.market.service.GroupService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.market.dao.GroupDAO;
import com.web.market.model.Group;
import com.web.market.model.User;

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
