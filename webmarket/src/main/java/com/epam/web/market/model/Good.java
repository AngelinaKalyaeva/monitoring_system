package com.epam.web.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Good {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer number;
    private byte[] image;
}
