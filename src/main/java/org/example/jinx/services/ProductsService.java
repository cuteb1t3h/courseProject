package org.example.jinx.services;

import org.example.jinx.models.Category;
import org.example.jinx.models.Product;
import org.example.jinx.repos.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductsService {
    private final ProductsRepo productsRepo;

    @Autowired
    public ProductsService(ProductsRepo productsRepo) {
        this.productsRepo = productsRepo;
    }

    public List<Product> findAll() {
        return productsRepo.findAll();
    }

    public Optional<Product> findById(long id) {
        return productsRepo.findById(id);
    }
    public List<Product> findByCategory(Category category) {
        return productsRepo.findByCategory(category);
    }
    @Transactional
    public void save(Product product) {
        productsRepo.save(product);
    }

}
