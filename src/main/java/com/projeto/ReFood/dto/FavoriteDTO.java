package com.projeto.ReFood.dto;

import jakarta.validation.constraints.NotNull;

public record FavoriteDTO(
    Long favoriteId,

    @NotNull(message = "O ID do usuário é obrigatório.") Long userId,

    @NotNull(message = "O ID do restaurante é obrigatório.") Long restaurantId) {
}