package com.web.market.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedGood {
    @NonNull
    private Integer orderId;

    @NonNull
    private Integer goodId;

    @NonNull
    private Integer number;

    @NonNull
    private BigDecimal price;
}
