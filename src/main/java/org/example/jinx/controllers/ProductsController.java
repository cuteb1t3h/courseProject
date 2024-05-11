package org.example.jinx.controllers;

import org.example.jinx.models.Category;
import org.example.jinx.models.Product;
import org.example.jinx.models.User;
import org.example.jinx.repos.CartRepo;
import org.example.jinx.services.CartService;
import org.example.jinx.services.ProductsService;
import org.example.jinx.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private final ProductsService productsService;
    private final UserService userService;
    private final CartService cartService;
    private final CartRepo cartRepo;

    @Autowired
    public ProductsController(ProductsService productsService, UserService userService, CartService cartService,
                              CartRepo cartRepo) {
        this.productsService = productsService;
        this.userService = userService;
        this.cartService = cartService;
        this.cartRepo = cartRepo;
    }

    @GetMapping("")
    public String catalog(Model model, @RequestParam(name = "category", required = false, defaultValue = "all") String category) {
        if (!category.equals("all")) {
            model.addAttribute("products", productsService.findByCategory(Category.valueOf(category)));
        }
        else {
            model.addAttribute("products", productsService.findAll());
        }
        model.addAttribute("categories", Category.values());
        return "products/products";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable("id") long id, Model model,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        if (productsService.findById(id).isPresent()) {
            Product product = productsService.findById(id).get();
            model.addAttribute("product", product);
            boolean flag = false;
            if (authUser != null) {
                User user = userService.findByUsername(authUser.getUsername()).get();
                model.addAttribute("cart", cartService.findByProductViaUser(user, product));
                flag = true;
            }
            model.addAttribute("flag", flag);
            return "products/product";
        }
        return "products/products";
    }

    @PatchMapping("/{id}/add_to_cart")
    public String addToCart(@PathVariable("id") long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser,
                            Model model) {
        User user = userService.findByUsername(authUser.getUsername()).get();
        Product product = productsService.findById(id).get();
        cartService.addProduct(user, product);
        model.addAttribute("cart", cartService.findByUser(user));
        return "redirect:/products/{id}";
    }

}
