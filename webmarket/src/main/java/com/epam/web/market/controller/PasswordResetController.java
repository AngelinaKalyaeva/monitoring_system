package com.epam.web.market.controller;

import com.epam.web.market.email.SMTPMailSender;
import com.epam.web.market.model.User;
import com.epam.web.market.service.UserService;
import com.epam.web.market.thymeleafform.FormCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.epam.web.market.constant.PageNames.FORGET_PASSWORD_PAGE_NAME;
import static com.epam.web.market.constant.PageNames.SUCCESS_EMAIL_PASS_PAGE_NAME;

@PreAuthorize("permitAll()")
@Controller
public class PasswordResetController {
    private SMTPMailSender mailSender;
    private UserService userService;

    @Autowired
    PasswordResetController(SMTPMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    @GetMapping(value = "/forget")
    public String showRegistrationPage(Model model) {
        model.addAttribute("resetEmail", new FormCommand());
        return FORGET_PASSWORD_PAGE_NAME;
    }

    @PostMapping(value = "/forget/submit")
    public String sendResetPasswordEmail(@ModelAttribute FormCommand email) {
        User user = null;
        if (!userService.checkUserExistByEmail(email.getTextField())) {
            return "redirect:/forget?error=true";
        } else {
            user = userService.getByEmail(email.getTextField());
        }
        try {
            mailSender.send(user.getEmail(), "Password recovery", "Your password: " + user.getPassword());
            return SUCCESS_EMAIL_PASS_PAGE_NAME;
        } catch (Exception ex) {
            return "redirect:/forget?error=true";
        }
    }
}
