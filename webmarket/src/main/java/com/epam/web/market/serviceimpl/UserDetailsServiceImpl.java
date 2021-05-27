package com.epam.web.market.serviceimpl;

import com.epam.web.market.model.User;
import com.epam.web.market.service.GroupService;
import com.epam.web.market.service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private GroupService groupService;
    private UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService, GroupService groupService){
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email);
        System.out.println(user.toString());
        UserBuilder builder = null;
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else if(user.getBlacklisted()) {
            throw new ExceptionInInitializerError("User in black list");
        } else {
            builder = withUsername(email);
            builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            builder.roles(groupService.getGroupById(user.getGroupId()).getName());
        }
        return builder.build();
    }
}
