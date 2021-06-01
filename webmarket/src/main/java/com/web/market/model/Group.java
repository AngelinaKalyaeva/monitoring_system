package com.web.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @NonNull
    private Integer id;

    @NonNull
    private String name;
}