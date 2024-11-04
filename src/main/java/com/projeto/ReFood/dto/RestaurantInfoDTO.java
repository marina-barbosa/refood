package com.projeto.ReFood.dto;

import java.time.LocalDateTime;

import com.projeto.ReFood.model.EnumRestaurantCategory;

public record RestaurantInfoDTO(
    Long restaurantId,
    String fantasy,
    String email,
    LocalDateTime dateCreation,
    EnumRestaurantCategory category,
    String urlBanner,
    String urlLogo,
    int quantityEvaluations,
    int totalEvaluations,
    String phone,
    String description,
    float averageRating
    // restaurantAddresses
    // restaurantHours
    ) {}
