package com.epam.web.market.controller;

import com.epam.web.market.model.User;
import com.epam.web.market.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epam.web.market.model.Good;
import com.epam.web.market.service.GoodService;

import static com.epam.web.market.constant.PageNames.GOODS_PAGE_ADDRESS_NAME;
import static com.epam.web.market.constant.PageNames.GOOD_CARD_PAGE;

@Controller
@RequiredArgsConstructor
public class GoodsController {
    private final GoodService goodService;
    private final MetricsApiService metricsApiService;

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/")
    public String showAllGoods(Model model) {
        metricsApiService.sendUrlAttendenceMetric("/");
        model.addAttribute("goods", goodService.getAllGoods());
        return GOODS_PAGE_ADDRESS_NAME;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/good/{id}")
    public String showGoodCard(@PathVariable("id") Integer goodId, @ModelAttribute User user, Model model) {
        long start = System.currentTimeMillis();
        metricsApiService.sendUrlAttendenceMetric(String.format("/good/%s", goodId));
        model.addAttribute("good", goodService.getGoodById(goodId));
        metricsApiService.sendSlaUrlTime(String.format("/good/%s", goodId), System.currentTimeMillis() - start);
        return GOOD_CARD_PAGE;
    }

    @RequestMapping(value = "/goodimage/{id:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) {
        byte[] image = goodService.getGoodById(id).getImage();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
