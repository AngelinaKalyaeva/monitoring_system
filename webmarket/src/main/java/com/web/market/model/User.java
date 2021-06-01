package com.web.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NonNull
    private Integer id;

    private String name;

    private String password;

    private Boolean blacklisted;

    private Integer groupId;

    private String email;

    public User(Integer ID) {
        this.id = ID;
    }

}
