package org.example.jinx.services;

import org.example.jinx.models.Cart;
import org.example.jinx.models.User;
import org.example.jinx.repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, CartService cartService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.cartService = cartService;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveNew(User user) {
        userRepo.save(user);
    }

    public Optional<User> findById(long id) {
        return userRepo.findById(id);
    }

    public boolean update(User user, String newPass) {
        User found = findByEmail(user.getEmail()).orElse(null);
        if (found == null) {
            return false;
        }

        found.setPassword(passwordEncoder.encode(newPass));

        userRepo.save(found);
        return true;
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(String password, String basePassword) {
        return passwordEncoder.matches(password, basePassword);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}
