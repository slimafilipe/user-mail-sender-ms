package dev.filipe.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record dto(
        @NotBlank String name,
        @NotBlank @Email String email) {
}
