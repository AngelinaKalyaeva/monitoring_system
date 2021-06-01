package com.epam.web.market.controller;

import com.epam.web.market.model.User;
import com.epam.web.market.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.epam.web.market.model.Good;
import com.epam.web.market.service.GoodService;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class GoodsController {
    private final GoodService goodService;
    private final MetricsApiService metricsApiService;

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/")
    public List<Good> showAllGoods() {
        metricsApiService.sendUrlAttendenceMetric("/");
        return goodService.getAllGoods();
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/good/{id}")
    public Good showGoodCard(@PathVariable("id") Integer goodId, @ModelAttribute User user) {
        long start = System.currentTimeMillis();
        metricsApiService.sendUrlAttendenceMetric(String.format("/good/%s", goodId));
        Good goodById = goodService.getGoodById(goodId);
        metricsApiService.sendSlaUrlTime(String.format("/good/%s", goodId), System.currentTimeMillis() - start);
        return goodById;
    }
}
