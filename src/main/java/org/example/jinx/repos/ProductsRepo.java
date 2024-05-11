package org.example.jinx.repos;

import org.example.jinx.models.Category;
import org.example.jinx.models.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends JpaRepository<Product, Long> {
    List<Product> findByCategory(@NotNull(message = "Must be not null") Category category);
}
