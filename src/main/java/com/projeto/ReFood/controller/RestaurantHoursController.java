package com.projeto.ReFood.controller;

import java.net.URI;
import java.util.List;

import com.projeto.ReFood.model.EnumDayOfWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.ReFood.dto.RestaurantHoursDTO;
import com.projeto.ReFood.service.RestaurantHoursService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurant-hours")
public class RestaurantHoursController {

  @Autowired
  private RestaurantHoursService restaurantHoursService;

  @GetMapping
  public ResponseEntity<List<RestaurantHoursDTO>> listAllHours() {
    List<RestaurantHoursDTO> hours = restaurantHoursService.getAllHours();
    return ResponseEntity.ok(hours);
  }

  @GetMapping("/{hoursId}")
  public ResponseEntity<RestaurantHoursDTO> getHoursById(@PathVariable Long hoursId) {
    RestaurantHoursDTO hoursDTO = restaurantHoursService.getHoursById(hoursId);
    return ResponseEntity.ok(hoursDTO);
  }

  @GetMapping("/today")
  public ResponseEntity<List<RestaurantHoursDTO>> getTodayHours() {
    EnumDayOfWeek today = EnumDayOfWeek.valueOf(java.time.LocalDate.now().getDayOfWeek().name());
    List<RestaurantHoursDTO> hours = restaurantHoursService.getHoursByDay(today);
    return ResponseEntity.ok(hours);
  }

  @GetMapping("/restaurant-id/{restaurantId}")
  public ResponseEntity<List<RestaurantHoursDTO>> getRestaurantHours(@PathVariable Long restaurantId) {
    List<RestaurantHoursDTO> hours = restaurantHoursService.getHoursByRestaurantId(restaurantId);
    return ResponseEntity.ok(hours);
  }

  @GetMapping("/restaurant")
  public ResponseEntity<List<RestaurantHoursDTO>> getRestaurantHours(@RequestHeader("Authorization") String token) {
    List<RestaurantHoursDTO> hours = restaurantHoursService.getHoursByRestaurant(token);
    return ResponseEntity.ok(hours);
  }

  @PostMapping
  public ResponseEntity<RestaurantHoursDTO> createHours(@RequestHeader("Authorization") String token,
      @Valid @RequestBody RestaurantHoursDTO hoursDTO) {
    RestaurantHoursDTO createdHours = restaurantHoursService.createHours(hoursDTO, token);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{hoursId}")
        .buildAndExpand(createdHours.id())
        .toUri();
    return ResponseEntity.created(location).body(createdHours);
  }

  @PutMapping("/{hoursId}")
  public ResponseEntity<RestaurantHoursDTO> updateHours(@PathVariable Long hoursId,
      @Valid @RequestBody RestaurantHoursDTO hoursDTO) {
    RestaurantHoursDTO updatedHours = restaurantHoursService.updateHours(hoursId, hoursDTO);
    return ResponseEntity.ok(updatedHours);
  }

  @DeleteMapping("/{hoursId}")
  public ResponseEntity<Void> deleteHours(@PathVariable Long hoursId) {
    restaurantHoursService.deleteHours(hoursId);
    return ResponseEntity.noContent().build();
  }
}
