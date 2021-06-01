package com.web.market.controller;

import com.web.market.model.Good;
import com.web.market.model.User;
import com.web.market.service.GoodService;
import com.web.market.service.UserService;

import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@ResponseBody
public class AdminController {
    private UserService userService;
    private GoodService goodService;

    @Autowired
    public AdminController(UserService userService, GoodService goodService) {
        this.userService = userService;
        this.goodService = goodService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/users")
    public List<User> showUsers() {
        return userService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/users/profile/{id}")
    public User showProfile(@PathVariable("id") Integer userId) {
        return userService.getById(userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/users/profile/{id}/edit")
    public User editUserProfile(@PathVariable("id") Integer userId) {
        return userService.getById(userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/users/profile/{id}/edit/submit", method = RequestMethod.POST)
    public void submitEditedUserProfile(@PathVariable("id") Integer userId, @ModelAttribute User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (userService.getById(userId).getEmail().equals(auth.getName()) &&
                !userService.getById(userId).getEmail().equals(user.getEmail())){
            auth.setAuthenticated(false);
            SecurityContextHolder.getContext().setAuthentication(auth);
            userService.update(user);
        }
        userService.update(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/good/{id}/edit")
    public Good editGoodCard(@PathVariable("id") Integer goodId) {
        return goodService.getGoodById(goodId);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/good/{id}/remove")
    public void removeGoodCard(@PathVariable("id") Integer goodId) {
        goodService.deleteGood(goodId);
        return;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/good/{id}/edit/submit")
    @SneakyThrows
    public Good submitEditGoodCard(@PathVariable("id") Integer goodId, @ModelAttribute Good good, @RequestParam("imagefile") MultipartFile image) {
        if (!image.isEmpty()) {
            good.setImage(image.getBytes());
        } else {
            good.setImage(goodService.getGoodById(goodId).getImage());
        }
        goodService.updateGood(good);
        return goodService.getGoodById(goodId);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/newgood/submit")
    @SneakyThrows
    public void submitNewGood(@ModelAttribute Good good, @RequestParam("imagefile") MultipartFile image) {
        good.setImage(image.getBytes());
        goodService.addGood(good);
        return;
    }
}
