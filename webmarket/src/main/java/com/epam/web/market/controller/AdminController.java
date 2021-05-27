package com.epam.web.market.controller;

import com.epam.web.market.model.Good;
import com.epam.web.market.model.User;
import com.epam.web.market.service.CartService;
import com.epam.web.market.service.GoodService;
import com.epam.web.market.service.UserService;
import com.epam.web.market.thymeleafform.FormCommand;

import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.epam.web.market.constant.PageNames.*;

@Controller
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
    public String showUsers(Model model) {
        model.addAttribute("userId", new FormCommand());
        model.addAttribute("users", userService.getAll());
        return USER_LIST_SHOW_PAGE;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/users/profile/{id}")
    public String showProfile(Model model, @PathVariable("id") Integer userId) {
        model.addAttribute("user", userService.getById(userId));
        return USER_PROFILE_FOR_ADMIN_PAGE;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/users/profile/{id}/edit")
    public String editUserProfile(Model model, @PathVariable("id") Integer userId) {
        model.addAttribute("user", userService.getById(userId));
        model.addAttribute("updatedUser", userService.getById(userId));
        return USER_PROFILE_FOR_ADMIN_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/users/profile/{id}/edit/submit", method = RequestMethod.POST)
    public String submitEditedUserProfile(@PathVariable("id") Integer userId, @ModelAttribute User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (userService.getById(userId).getEmail().equals(auth.getName()) &&
                !userService.getById(userId).getEmail().equals(user.getEmail())){
            auth.setAuthenticated(false);
            SecurityContextHolder.getContext().setAuthentication(auth);
            userService.update(user);
            return "redirect:/logout";
        }
        userService.update(user);
        model.addAttribute("user", userService.getById(userId));
        return "redirect:/users/profile/" + userId;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/good/{id}/edit")
    public String editGoodCard(Model model, @PathVariable("id") Integer goodId) {
        model.addAttribute("good", goodService.getGoodById(goodId));
        model.addAttribute("updatedUser", goodService.getGoodById(goodId));
        return GOOD_CARD_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/good/{id}/remove")
    public String removeGoodCard(Model model, @PathVariable("id") Integer goodId) {
        goodService.deleteGood(goodId);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/good/{id}/updategoodimage")
    @SneakyThrows
    public String updateGoodImage(@PathVariable("id") int id, @RequestParam("image") MultipartFile image) {
        return "redirect:/good/" + id;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/good/{id}/edit/submit")
    @SneakyThrows
    public String submitEditGoodCard(@PathVariable("id") Integer goodId, @ModelAttribute Good good, Model model, @RequestParam("imagefile") MultipartFile image) {
        if (!image.isEmpty()) {
            good.setImage(image.getBytes());
        } else {
            good.setImage(goodService.getGoodById(goodId).getImage());
        }
        goodService.updateGood(good);
        Good updatedGood = goodService.getGoodById(goodId);
        model.addAttribute("good", updatedGood);
        return "redirect:/good/" + goodId;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/newgood")
    public String showNewGoodPage(Model model) {
        model.addAttribute("good", new Good());
        return NEW_GOOD_PAGE;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/newgood/submit")
    @SneakyThrows
    public String submitNewGood(@ModelAttribute Good good, @RequestParam("imagefile") MultipartFile image) {
        good.setImage(image.getBytes());
        goodService.addGood(good);
        return "redirect:/";
    }
}
