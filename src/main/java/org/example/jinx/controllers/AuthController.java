package org.example.jinx.controllers;

import org.example.jinx.models.EmailSend;
import org.example.jinx.models.User;
import org.example.jinx.services.EmailService;
import org.example.jinx.services.UserService;
import org.example.jinx.util.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final EmailService emailService;

    @Autowired
    public AuthController(UserService userService, UserValidator userValidator, EmailService emailService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.emailService = emailService;
    }

    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/";
    }
    @GetMapping("/register")
    public String reg(@ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (!bindingResult.hasErrors()) {
            user.setPassword(userService.encode(user.getPassword()));
            userService.saveNew(user);
            return "/auth/login";
        }
        return "/auth/registration";
    }

    @GetMapping("/")
    public String emptyRequest(Model model) {
        model.addAttribute("contactForm", new EmailSend());
        return "index";
    }

    @PostMapping("/")
    public String contact_form(@ModelAttribute("contactForm") @Valid EmailSend emailSend, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            emailService.sendMail(emailSend);
        }
        return "index";
    }

    @GetMapping("forgot_password")
    public String forgot_password(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("sent", false);
        return "/auth/forgot_password";
    }

    @PostMapping("forgot_password")
    public String forgot_passwordPost(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        String newPassword = userValidator.generatePassword();
        if (!userService.update(user, newPassword)) {
            bindingResult.rejectValue("email", "email", "User doesn't exists");
        } else {
            emailService.sendForgotPassword(user.getEmail(), newPassword);
            model.addAttribute("sent", true);
        }
        return "/auth/forgot_password";
    }

}
