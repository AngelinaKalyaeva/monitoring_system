package com.epam.web.market.thymeleafform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormCommand {
    String textField;
    Integer intField;
}
