package com.projeto.ReFood.service;

import java.util.List;
import java.util.stream.Collectors;

import com.projeto.ReFood.model.EnumDayOfWeek;
import com.projeto.ReFood.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.RestaurantHoursDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.RestaurantHours;
import com.projeto.ReFood.repository.RestaurantHoursRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class RestaurantHoursService {

  @Autowired
  private RestaurantHoursRepository restaurantHoursRepository;

  @Autowired
  private UtilityService utilityService;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Transactional(readOnly = true)
  public List<RestaurantHoursDTO> getAllHours() {
    return restaurantHoursRepository.findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public RestaurantHoursDTO getHoursById(Long hoursId) {
    return restaurantHoursRepository.findById(hoursId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public RestaurantHoursDTO createHours(@Valid RestaurantHoursDTO hoursDTO, String token) {
    Long restaurantId = jwtTokenProvider.extractUserId(token);
    RestaurantHours hours = convertToEntity(hoursDTO);

    utilityService.associateRestaurant(hours::setRestaurant, restaurantId);
    hours = restaurantHoursRepository.save(hours);

    return convertToDTO(hours);
  }

  @Transactional
  public RestaurantHoursDTO updateHours(Long hoursId, @Valid RestaurantHoursDTO hoursDTO) {
    RestaurantHours hours = restaurantHoursRepository.findById(hoursId)
        .orElseThrow(() -> new NotFoundException());

    hours.setDayOfWeek(hoursDTO.dayOfWeek());
    hours.setOpeningTime(hoursDTO.openingTime());
    hours.setClosingTime(hoursDTO.closingTime());

    utilityService.associateRestaurant(hours::setRestaurant, hoursDTO.restaurantId());

    hours = restaurantHoursRepository.save(hours);
    return convertToDTO(hours);
  }

  @Transactional
  public void deleteHours(Long hoursId) {
    if (!restaurantHoursRepository.existsById(hoursId)) {
      throw new NotFoundException();
    }
    restaurantHoursRepository.deleteById(hoursId);
  }

  private RestaurantHoursDTO convertToDTO(RestaurantHours hours) {
    return new RestaurantHoursDTO(
        hours.getId(),
        hours.getDayOfWeek(),
        hours.getOpeningTime(),
        hours.getClosingTime(),
        hours.getRestaurant().getRestaurantId());
  }

  private RestaurantHours convertToEntity(RestaurantHoursDTO hoursDTO) {
    RestaurantHours hours = new RestaurantHours();
    hours.setId(hoursDTO.id());
    hours.setDayOfWeek(hoursDTO.dayOfWeek());
    hours.setOpeningTime(hoursDTO.openingTime());
    hours.setClosingTime(hoursDTO.closingTime());
    hours.setId(hoursDTO.restaurantId());

    return hours;
  }

  @Transactional
  public List<RestaurantHoursDTO> getHoursByDay(EnumDayOfWeek day) {
    return restaurantHoursRepository.findByDayOfWeek(day).stream()
        .map(this::convertToDTO)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<RestaurantHoursDTO> getHoursByRestaurantId(Long restaurantId) {
    return restaurantHoursRepository.findByRestaurantId(restaurantId).stream()
        .map(this::convertToDTO)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<RestaurantHoursDTO> getHoursByRestaurant(String token) {
    Long restaurantId = jwtTokenProvider.extractUserId(token);
    return restaurantHoursRepository.findByRestaurantId(restaurantId).stream()
        .map(this::convertToDTO)
        .toList();
  }

}
