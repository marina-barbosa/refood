package com.projeto.ReFood.service;

import com.projeto.ReFood.repository.FavoriteRepository;

import com.projeto.ReFood.security.JwtTokenProvider;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.FavoriteDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.Favorite;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class FavoriteService {

  @Autowired
  private FavoriteRepository favoriteRepository;

  @Autowired
  private UtilityService utilityService;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Transactional(readOnly = true)
  public List<FavoriteDTO> getAllFavorites() {
    return favoriteRepository
            .findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public FavoriteDTO getFavoriteById(Long favoriteId) {
    return favoriteRepository.findById(favoriteId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional(readOnly = true)
  public List<FavoriteDTO> getFavoriteByUserId(String token) {
    Long userId = jwtTokenProvider.extractUserId(token);
    return favoriteRepository.findByUser_UserId(userId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
  }

  @Transactional
  public FavoriteDTO createFavorite(@Valid Long restaurantId, String token) {
    Favorite favorite = new Favorite();
    Long userID = jwtTokenProvider.extractUserId(token);
    utilityService.associateUser(favorite::setUser, userID);
    utilityService.associateRestaurant(favorite::setRestaurant, restaurantId);

    favorite = favoriteRepository.save(favorite);

    return convertToDTO(favorite);
  }

  @Transactional
  public FavoriteDTO updateFavorite(Long favoriteId, @Valid FavoriteDTO favoriteDTO) {
    Favorite favorite = favoriteRepository.findById(favoriteId)
        .orElseThrow(() -> new NotFoundException());

    utilityService.associateUser(favorite::setUser, favoriteDTO.userId());
    utilityService.associateRestaurant(favorite::setRestaurant, favoriteDTO.restaurantId());

    favorite = favoriteRepository.save(favorite);
    return convertToDTO(favorite);
  }

  @Transactional
  public void deleteFavorite(Long favoriteId) {
    if (!favoriteRepository.existsById(favoriteId)) {
      throw new NotFoundException();
    }
    favoriteRepository.deleteById(favoriteId);
  }

  private FavoriteDTO convertToDTO(Favorite favorite) {
    return new FavoriteDTO(
        favorite.getFavoriteId(),
        favorite.getUser().getUserId(),
        favorite.getRestaurant().getRestaurantId());
  }
}