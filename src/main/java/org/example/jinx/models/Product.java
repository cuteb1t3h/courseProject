package org.example.jinx.models;

import jakarta.mail.MultipartDataSource;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotNull(message = "Must be not null")
    @Size(min = 2, message = "Min size is 2")
    private String name;

    @Column(name = "category")
    @NotNull(message = "Must be not null")
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(name = "price")
    @NotNull(message = "Must be not null")
    @Min(value = 10, message = "min price is 10")
    private int price;

    @Column(name = "description")
    @Size(min = 10, message = "Min size of description is 10 chars")
    @NotNull(message = "Must be not null")
    private String description;

    @NotEmpty
    @Column(name = "image")
    private String image;
}
