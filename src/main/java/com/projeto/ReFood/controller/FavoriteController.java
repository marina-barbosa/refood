package com.projeto.ReFood.controller;

import com.projeto.ReFood.service.FavoriteService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.ReFood.dto.FavoriteDTO;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

  @Autowired
  private FavoriteService favoriteService;

  @GetMapping
  public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
    List<FavoriteDTO> favorites = favoriteService.getAllFavorites();
    return ResponseEntity.ok(favorites);
  }

  @GetMapping("/{favoriteId}")
  public ResponseEntity<FavoriteDTO> getFavoriteById(@PathVariable Long favoriteId) {
    FavoriteDTO favoriteDTO = favoriteService.getFavoriteById(favoriteId);
    return ResponseEntity.ok(favoriteDTO);
  }

  @GetMapping("/user")
  public ResponseEntity<List<FavoriteDTO>> getFavoritesByUserId(@RequestHeader("Authorization") String token) {
    List<FavoriteDTO> favorites = favoriteService.getFavoriteByUserId(token);
    return ResponseEntity.ok(favorites);
  }

  @PostMapping
  public ResponseEntity<FavoriteDTO> createFavorite(@RequestHeader("Authorization") String token,@Valid @RequestBody Long restaurantId) {
    FavoriteDTO createdFavorite = favoriteService.createFavorite(restaurantId, token);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{favoriteId}")
        .buildAndExpand(createdFavorite.favoriteId())
        .toUri();
    return ResponseEntity.created(location).body(createdFavorite);
  }

  @PutMapping("/{favoriteId}")
  public ResponseEntity<FavoriteDTO> updateFavorite(@PathVariable Long favoriteId,
      @Valid @RequestBody FavoriteDTO favoriteDTO) {
    FavoriteDTO updatedFavorite = favoriteService.updateFavorite(favoriteId, favoriteDTO);
    return ResponseEntity.ok(updatedFavorite);
  }

  @DeleteMapping("/{favoriteId}")
  public ResponseEntity<Void> deleteFavorite(@PathVariable Long favoriteId) {
    favoriteService.deleteFavorite(favoriteId);
    return ResponseEntity.noContent().build();
  }
}
