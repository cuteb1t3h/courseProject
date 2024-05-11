package org.example.jinx.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSend {

    @NotEmpty(message = "Empty name")
    private String name;

    @NotEmpty(message = "Empty email")
    private String email;

    @NotEmpty(message = "Empty title")
    private String title;

    @NotEmpty(message = "Empty comment")
    private String comment;
}