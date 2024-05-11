package org.example.jinx.services;

import org.example.jinx.models.Cart;
import org.example.jinx.models.Product;
import org.example.jinx.models.User;
import org.example.jinx.repos.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartRepo cartRepo;
    private final ProductsService productsService;

    @Autowired
    public CartService(CartRepo cartRepo,
                       ProductsService productsService) {
        this.cartRepo = cartRepo;
        this.productsService = productsService;
    }

    @Transactional
    public void save(Cart cart) {
        cartRepo.save(cart);
    }

    @Transactional
    public void update(Cart updCart, long id) {
        updCart.setId(id);
        save(updCart);
    }

    public List<Cart> findByUser(User user) {
        return cartRepo.findByUser(user);
    }

    @Transactional
    public void addProduct(User user, Product product) {
        Cart cart = new Cart(user, product);
        cartRepo.save(cart);
    }

    @Transactional
    public void increaseQuantity(User user, Product product) {
        Cart cart = findByProductViaUser(user, product);
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + 1);
            this.update(cart, cart.getId());
        }
    }

    @Transactional
    public void decreaseQuantity(User user, Product product) {
        Cart cart = findByProductViaUser(user, product);
        if (cart != null) {
            if (cart.getQuantity() > 1) {
                cart.setQuantity(cart.getQuantity() - 1);
                this.update(cart, cart.getId());
            }
            else {
                cartRepo.deleteCartById(cart.getId());
            }
        }
    }

    public Cart findByProductViaUser(User user, Product product) {
        List<Cart> products = this.findProductsByUser(user);
        for (Cart productsByUser: products) {
            if (productsByUser.getProduct().getId() == product.getId()) {
                return productsByUser;
            }
        }
        return null;
    }
    @Transactional
    public void deleteAllCart(User user) {
        cartRepo.deleteAllByUser(user.getId());
    }

    @Transactional
    public void deleteProductById(long productId, User user) {
        Cart cart = this.findByProductViaUser(user, productsService.findById(productId).get());
        cartRepo.deleteCartById(cart.getId());
    }

    public List<Cart> findProductsByUser(User user) {
        return cartRepo.findByUser(user);
    }
    public int sum(List<Cart> carts) {
        int sum = 0;
        for (Cart cart: carts) {
            sum += cart.getProduct().getPrice() * cart.getQuantity();
        }
        return sum;
    }

    public List<Cart> sortById(List<Cart> carts) {
        for (int i = 0; i < carts.size() - 1; i++) {
            for (int j = i; j < carts.size(); j++) {
                 if (carts.get(i).getId() > carts.get(j).getId()) {
                     Collections.swap(carts, i, j);
                 }
            }
        }
        return carts;
    }
}
