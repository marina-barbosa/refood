package com.projeto.ReFood.dto;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record RestaurantDTO(
        Long restaurantId,

        @NotBlank(message = "Nome fantasia não pode estar vazio.")
        @Size(min = 3, max = 100, message = "Nome fantasia deve ter entre 3 e 100 caracteres.")
        String fantasy,

        @NotBlank(message = "CNPJ não pode estar vazio.")
        @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter exatamente 14 dígitos.")
        String cnpj,

        @NotBlank(message = "A categoria é obrigatória.")
        String category,

        @NotBlank(message = "Email não pode estar vazio.")
        @Email(message = "Email deve ser válido.")
        @Size(max = 100, message = "Email deve ter no máximo 100 caracteres.")
        String email,

        @NotEmpty(message = "Telefone não pode estar vazio.")
        @Pattern(regexp = "\\d{11}", message = "Telefone deve conter exatamente 11 dígitos.")
        String phone,

        @NotBlank(message = "Senha não pode estar vazia.")
        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres.")
        String password,

        @Pattern(regexp = "^(http|https)://.*$", message = "A URL do banner deve ser válida.")
        String urlBanner,

        @Pattern(regexp = "^(http|https)://.*$", message = "A URL do logo deve ser válida.")
        String urlLogo,

        @Min(value = 0, message = "A quantidade de avaliações deve ser no mínimo 0.")
        int quantityEvaluations,

        @Min(value = 0, message = "O total de avaliações deve ser no mínimo 0.")
        int totalEvaluations,

        @Min(value = 0, message = "A avaliação média deve ser no mínimo 0.")
        @Max(value = 5, message = "A avaliação média deve ser no máximo 5.")
        float averageRating,

        @Min(value = 20, message = "A descrição deve ter no mínimo 20 caracteres")
        String description,

        @NotNull(message = "O endereço é obrigatório")
        @Valid()
        AddressDTO address,

        LocalDateTime dateCreation,
        LocalDateTime lastLogin
) {
}
