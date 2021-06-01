package com.epam.web.market.controller;

import com.epam.web.market.entity.OrderedGood;
import com.epam.web.market.service.MetricsApiService;
import com.epam.web.market.service.OrderService;
import com.epam.web.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@PreAuthorize("hasRole('USER')")
@Controller
@ResponseBody
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final MetricsApiService metricsApiService;

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/orders")
    public List<OrderedGood> showAllOrders() {
        metricsApiService.sendUrlAttendenceMetric("/orders");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return orderService.getOrderedGoodsForUser(userService.getByEmail(auth.getName()));
    }
}
