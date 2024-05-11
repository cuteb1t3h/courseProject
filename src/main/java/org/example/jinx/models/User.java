package org.example.jinx.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
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

    @Column(name = "password")
    @NotEmpty(message = "Password must not be empty")
    @Size(min = 5, message = "Password length must be between 5 and 10 chars")
    private String password;

    @Column(name = "email")
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Invalid email")
    private String email;

    @Column(name = "date_of_birth")
    @NotNull(message = "Date must not be empty")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;


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

    public String getDateOfBirthFormatted(){
        return new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth);
    }
}
