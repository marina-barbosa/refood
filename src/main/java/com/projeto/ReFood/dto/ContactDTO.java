package com.projeto.ReFood.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactDTO(
    Long contactId,

    @NotBlank(message = "A descrição do contato é obrigatória.") 
    @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres.") 
    String description,

    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "Telefone deve conter exatamente 11 dígitos.")
    String phone,

    Long restaurantId) {
}