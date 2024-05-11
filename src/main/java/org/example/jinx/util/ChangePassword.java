package org.example.jinx.util;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {
    @NotEmpty(message = "Password must not be empty")
    private String oldPassword;
    @NotEmpty(message = "Password must not be empty")
    @Length(min = 5, message = "Min length is 5")
    private String newPassword;
    @NotEmpty(message = "Password must not be empty")
    @Length(min = 5, message = "Min length is 5")
    private String repeatedPassword;
}
