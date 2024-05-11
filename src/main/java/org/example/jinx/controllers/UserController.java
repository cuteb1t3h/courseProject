package org.example.jinx.controllers;

import org.example.jinx.models.Cart;
import org.example.jinx.models.User;
import org.example.jinx.services.CartService;
import org.example.jinx.services.UserService;
import org.example.jinx.util.ChangePassword;
import org.example.jinx.util.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final CartService cartService;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator, CartService cartService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.cartService = cartService;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user, Model model) {
        Optional<User> a = userService.findByUsername(user.getUsername());
        if (a.isPresent()) {
            User myUser = a.get();
            model.addAttribute("profile_user", myUser);
            return "user/profile";
        }
        return "redirect:auth/login";
    }

    @GetMapping("change_password")
    public String change_password(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user, Model model) {
        Optional<User> a = userService.findByUsername(user.getUsername());
        if (a.isPresent()) {
            model.addAttribute("pass", new ChangePassword());
            return "/user/change_password";
        }
        return "/auth/login";
    }

    @PostMapping("change_password")
    public String change_password(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser,
                                  @ModelAttribute("pass") @Valid ChangePassword user, BindingResult bindingResult, Model model) {
        Optional<User> a = userService.findByUsername(authUser.getUsername());
        if (a.isPresent()) {
            User myUser = a.get();
            if (bindingResult.hasErrors()) {
                return "/user/change_password";
            }
            if (!userService.matches(user.getOldPassword(), myUser.getPassword())) {
                bindingResult.rejectValue("oldPassword", "old password", "invalid password");
                return "/user/change_password";
            }

            if (user.getNewPassword().equals(user.getRepeatedPassword())) {
                userService.update(myUser, user.getNewPassword());
                return "redirect:/user/profile";
            } else {
                bindingResult.rejectValue("newPassword", "new password", "passwords don't match");
            }
        }
        return "/user/change_password";
    }

    @GetMapping("/cart")
    public String cart(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser,
                       Model model) {
        User user = userService.findByUsername(authUser.getUsername()).get();
        List<Cart> carts = cartService.findProductsByUser(user);
        cartService.sortById(carts);
        model.addAttribute("products", carts);
        model.addAttribute("sum", cartService.sum(carts));
        return "/user/cart";
    }

    @PatchMapping("/cart/{product_id}/increase")
    public String increaseQuantity(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser,
                                   @PathVariable("product_id") long product_id) {
        User user = userService.findByUsername(authUser.getUsername()).get();
        List<Cart> carts = cartService.findProductsByUser(user);
        for (Cart product: carts) {
            if (product.getProduct().getId() == product_id) {
                cartService.increaseQuantity(user, product.getProduct());
            }
        }
        return "redirect:/user/cart";
    }

    @PatchMapping("/cart/{product_id}/decrease")
    public String decreaseQuantity(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser,
                                   @PathVariable("product_id") long product_id) {
        User user = userService.findByUsername(authUser.getUsername()).get();
        List<Cart> carts = cartService.findProductsByUser(user);
        for (Cart product: carts) {
            if (product.getProduct().getId() == product_id) {
                cartService.decreaseQuantity(user, product.getProduct());
            }
        }
        return "redirect:/user/cart";
    }

    @DeleteMapping("/cart/{product_id}/delete_from_cart")
    public String deleteFromCart(@PathVariable("product_id") long productId, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser,
                                 Model model){
        User user = userService.findByUsername(authUser.getUsername()).get();
        cartService.deleteProductById(productId, user);
        return "redirect:/user/cart";
    }

    @DeleteMapping("/cart/delete_cart")
    public String deleteCart(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        User user = userService.findByUsername(authUser.getUsername()).get();
        cartService.deleteAllCart(user);
        return "redirect:/user/cart";
    }
}
