package com.web.market.service;

import java.util.List;

import com.web.market.model.Group;
import com.web.market.model.User;
import lombok.NonNull;

public interface GroupService {
    List<Group> getAllGroups();

    Group getGroupWithUser(@NonNull User user);

    Group getGroupById(@NonNull Integer id);

    Group getGroupByName(@NonNull String name);

    void update(@NonNull Group group);

    void removeGroup(@NonNull Integer id);

    void addGroup(@NonNull Group group);
}
