package com.epam.web.market.controller;

import com.epam.web.market.service.CartService;
import com.epam.web.market.service.GoodService;
import com.epam.web.market.service.MetricsApiService;
import com.epam.web.market.service.UserService;
import com.epam.web.market.thymeleafform.FormCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.epam.web.market.constant.PageNames.USER_CART_EMPTY_PAGE;
import static com.epam.web.market.constant.PageNames.USER_CART_SHOW_PAGE;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final GoodService goodService;
    private final MetricsApiService metricsApiService;

    @Scope("session")
    @GetMapping(value = "/cart")
    public String viewCart (Model model, HttpSession session) {
        long start = System.currentTimeMillis();
        metricsApiService.sendUrlAttendenceMetric("/cart");
            ConcurrentHashMap<Integer, Integer> userCart = cartService.getUserCart(userService.getByEmail
                    (SecurityContextHolder.getContext().getAuthentication()
                            .getName()).getId());
            List<Integer> userCartGoods = cartService.getUserCartGoods(userService.getByEmail
                    (SecurityContextHolder.getContext().getAuthentication()
                            .getName()).getId());
            session.setAttribute("goods", goodService);
            session.setAttribute("userCart", userCart);
            session.setAttribute("userCartGoods", userCartGoods);
            model.addAttribute("address", new FormCommand());
            model.addAttribute("amount", new FormCommand());

            if (cartService.getAmount(userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId()).equals(0)) {
                return USER_CART_EMPTY_PAGE;
            }

        metricsApiService.sendSlaUrlTime("/cart", System.currentTimeMillis() - start);
            return USER_CART_SHOW_PAGE;
    }

    @Scope("session")
    @GetMapping(value = "/cart/remove/{id}")
    public String remove (HttpSession session, @PathVariable("id") Integer goodId) {
        metricsApiService.sendUrlAttendenceMetric(String.format("/cart/remove/%s", goodId));
        cartService.remove(userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId(), goodId);
        ConcurrentHashMap<Integer, Integer> userCart =  cartService.getUserCart(userService.getByEmail
                (SecurityContextHolder.getContext().getAuthentication()
                        .getName()).getId());
        List<Integer> userCartGoods = cartService.getUserCartGoods(userService.getByEmail
                (SecurityContextHolder.getContext().getAuthentication()
                        .getName()).getId());

        session.setAttribute("goods", goodService);
        session.setAttribute("userCart", userCart);
        session.setAttribute("userCartGoods", userCartGoods);

        if (cartService.getAmount(userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId()).equals(0)) {
            return "redirect:/";
        }

        return "redirect:/cart";
    }

    @RequestMapping(value = "/buy")
    public String buy (Model model, @ModelAttribute FormCommand form) {
        long start = System.currentTimeMillis();
        System.out.println("hello");
        if (Math.random() <= 0.3) {
            return "redirect:/orders";
        }
        metricsApiService.sendUrlAttendenceMetric("/buy");
        String address = "123";
        Integer userid = (userService.getByEmail("Jack@gmail.com").getId());
        int amount = cartService.buy(userid, address);
        metricsApiService.sendDynamicSaleMetric(amount);
        metricsApiService.sendSlaUrlTime("/buy", System.currentTimeMillis() - start);
        return "redirect:/orders";
    }

    @RequestMapping(value = "/good/{id}/add")
    public String editGoodCard(Model model, @PathVariable("id") Integer goodId) {
        long start = System.currentTimeMillis();
        metricsApiService.sendUrlAttendenceMetric(String.format("/good/%s/add", goodId));
        cartService.add(userService.getByEmail("Jack@gmail.com").getId(), goodId, 1);
        metricsApiService.sendSlaUrlTime(String.format("/good/%s/add", goodId), System.currentTimeMillis() - start);
        return "redirect:/?added";
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/cart/change/{id}")
    public String changeNumberInOrder(Model model, @PathVariable("id") Integer goodId, @ModelAttribute FormCommand amount) {
        metricsApiService.sendUrlAttendenceMetric(String.format("/cart/change/%s", goodId));
        cartService.setAmount(userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId(), goodId, amount.getIntField());
        return "redirect:/cart";
    }
}
