package com.epam.web.market.controller;

import com.epam.web.market.model.User;
import com.epam.web.market.service.CartService;
import com.epam.web.market.service.MetricsApiService;
import com.epam.web.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.epam.web.market.constant.PageNames.*;
import static org.thymeleaf.util.StringUtils.isEmpty;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MetricsApiService metricsApiService;

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/registration")
    public String showRegistrationPage(Model model) {
        metricsApiService.sendUrlAttendenceMetric("/registration");
        model.addAttribute("newUser", new User());
        return REGISTRATION_PAGE_ADDRESS_NAME;
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/registration/submit")
    public String addNewUser(@ModelAttribute User user) {
        metricsApiService.sendUrlAttendenceMetric("/registration/submit");
        if (!isEmpty(user.getEmail()) && !isEmpty(user.getName()) && !isEmpty(user.getPassword())) {
            if (userService.checkUserExistByEmail(user.getEmail())) {
                return "redirect:/registration?error=true";
            }
            userService.createUser(user.getName(), user.getEmail(), user.getPassword(), 1);
            return LOGIN_PAGE_ADDRESS_NAME;
        } else {
            return "redirect:/registration?error=true";
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/profile")
    public String showProfile(Model model) {
        metricsApiService.sendUrlAttendenceMetric("/profile");
        model.addAttribute("user", userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return USER_PROFILE_SHOW_PAGE;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/profile/edit")
    public String editUserProfile (Model model) {
        metricsApiService.sendUrlAttendenceMetric("/profile/edit");
        model.addAttribute("user", userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("updatedUser", userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return USER_PROFILE_EDIT_PAGE;
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/profile/edit/submit")
    public String submitEditedUserProfile (@ModelAttribute User user, Model model) {
        metricsApiService.sendUrlAttendenceMetric("/profile/edit/submit");
        User updatedUser = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        updatedUser = userService.changeName(updatedUser, user.getName());
        updatedUser = userService.changePassword(updatedUser, user.getPassword());
        if (!user.getEmail().equals(updatedUser.getEmail())) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            auth.setAuthenticated(false);
            SecurityContextHolder.getContext().setAuthentication(auth);
            userService.changeMail(updatedUser, user.getEmail());
            return "redirect:/logout";
        }
        model.addAttribute("user", updatedUser);
        return "redirect:/profile";
    }
}
