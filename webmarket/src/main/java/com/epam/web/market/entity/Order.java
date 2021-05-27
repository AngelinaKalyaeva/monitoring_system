package com.epam.web.market.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @NonNull
    private Integer id;

    @NonNull
    private Integer userId;

    @NonNull
    private String address;
}
