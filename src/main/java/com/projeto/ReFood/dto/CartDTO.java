package com.projeto.ReFood.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartDTO(
    Long cartId,

    @Min(value = 0, message = "O valor total deve ser maior ou igual a zero.")
    float totalValue,

    @NotNull(message = "O ID do usuário não pode ser nulo.")
    Long userId

) {}
