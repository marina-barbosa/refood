package com.projeto.ReFood.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RestaurantUpdateDTO(
        Long restaurantId,

        @NotBlank(message = "Nome fantasia não pode estar vazio.")
        @Size(min = 3, max = 100, message = "Nome fantasia deve ter entre 3 e 100 caracteres.")
        String fantasy,

        @NotBlank(message = "CNPJ não pode estar vazio.")
        @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter exatamente 14 dígitos.")
        String cnpj,

        @NotBlank(message = "A categoria é obrigatória.")
        String category,

        @NotEmpty(message = "Telefone não pode estar vazio.")
        @Pattern(regexp = "\\d{11}", message = "Telefone deve conter exatamente 11 dígitos.")
        String phone,

        @Pattern(regexp = "^(http|https)://.*$", message = "A URL do banner deve ser válida.")
        String urlBanner,

        @Pattern(regexp = "^(http|https)://.*$", message = "A URL do logo deve ser válida.")
        String urlLogo,

        @Size(min = 20, message = "A descrição deve ter no mínimo 20 caracteres")
        String description

) {
}
