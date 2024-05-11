package org.example.jinx.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    @NotEmpty(message = "Username must not be empty")
    @Size(min = 2, max = 25, message = "Username length must be between 2 and 25 chars")
    private String username;

    @Size(min = 5, message = "Password length must be between 5 and 10 chars")
    @Column(name = "password")
    @NotEmpty(message = "Password must not be empty")
    private String password;

    @Column(name = "date_of_birth")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd.MM.yyyy")
    private Date dateOfBirth;

    @Column(name = "email")
    @Email(message = "Invalid email")
    private String email;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth + '\'' +
                ", email=" + email +
                '}';
    }
}
