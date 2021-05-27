package com.epam.web.market.controller;

import com.epam.web.market.service.MetricsApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.epam.web.market.constant.PageNames.LOGIN_PAGE_ADDRESS_NAME;
import static com.epam.web.market.constant.PageNames.LOGOUT_PAGE_ADDRESS_NAME;

@PreAuthorize("permitAll()")
@Controller
@RequiredArgsConstructor
public class LogController {
    private final MetricsApiService metricsApiService;

    @GetMapping(value = "/login")
    public String showLogInPage() {
        metricsApiService.sendUrlAttendenceMetric("/login");
        return LOGIN_PAGE_ADDRESS_NAME;
    }

    @GetMapping(value = "/logout.done")
    public String showLogOutPage() {
        metricsApiService.sendUrlAttendenceMetric("/logout.done");
        return LOGOUT_PAGE_ADDRESS_NAME;
    }
}
