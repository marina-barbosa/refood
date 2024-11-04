package com.projeto.ReFood.service;

import com.projeto.ReFood.dto.RestaurantDTO;
import com.projeto.ReFood.dto.RestaurantUpdateDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.CnpjAlreadyExistsException;
import com.projeto.ReFood.exception.GlobalExceptionHandler.EmailAlreadyExistsException;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.EnumRestaurantCategory;
import com.projeto.ReFood.model.Restaurant;
import com.projeto.ReFood.repository.RestaurantRepository;
import com.projeto.ReFood.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class
RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final UtilityService utilityService;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional(readOnly = true)
  public List<RestaurantDTO> getAllRestaurants() {
    return restaurantRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<RestaurantDTO> getRestaurants(Pageable pageable) {
    return restaurantRepository
            .findAll(pageable)
            .map(this::convertToDTO);
  }

  @Transactional(readOnly = true)
  public RestaurantDTO getRestaurantById(String token) {
    Long userId = jwtTokenProvider.extractUserId(token);
    return restaurantRepository.findById(userId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional(readOnly = true)
  public RestaurantDTO getRestaurantInfoByToken(String token) {
    Long userId = jwtTokenProvider.extractUserId(token);

    Restaurant restaurant = restaurantRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException());

    return convertToDTO(restaurant);

  }

  @Transactional
public RestaurantDTO createRestaurant(@Valid RestaurantDTO restaurantDTO) {

    if (!utilityService.isEmailUnique(restaurantDTO.email())) {
        throw new EmailAlreadyExistsException();
    }

    if (restaurantRepository.existsByCnpj(restaurantDTO.cnpj())) {
        throw new CnpjAlreadyExistsException();
    }

    Restaurant restaurant = convertToEntity(restaurantDTO);
    restaurant.setPassword(passwordEncoder.encode(restaurant.getPassword()));
    restaurant.setDateCreation(LocalDateTime.now());
    restaurant.setUrlBanner("https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/644020ab-f6c8-4871-be38-f1e70ccbabd4_banner.png?alt=media");
    restaurant.setUrlLogo("https://firebasestorage.googleapis.com/v0/b/refood-storage.appspot.com/o/2234dc33-7c9f-42c5-a1e4-57e9f96ebb3e_perfil.png?alt=media");
    restaurant.setLastLogin(null);

    restaurant = restaurantRepository.save(restaurant);

    utilityService.addAddressToRestaurant(restaurant, restaurantDTO.address());

    return convertToDTO(restaurant);
}


  @Transactional
  public RestaurantUpdateDTO updateRestaurant(String token, @Valid RestaurantUpdateDTO restaurantDTO) {
    Long userId = jwtTokenProvider.extractUserId(token);
    Restaurant restaurant = restaurantRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException());

//        if (!utilityService.isEmailUnique(restaurantDTO.email())) {
//            throw new EmailAlreadyExistsException();
//        }

//        if (restaurantRepository.existsByCnpj(restaurantDTO.cnpj())) {
//            throw new CnpjAlreadyExistsException();
//        }

    restaurant.setCnpj(restaurantDTO.cnpj());
    restaurant.setCategory(EnumRestaurantCategory.valueOf(restaurantDTO.category()));
    restaurant.setFantasy(restaurantDTO.fantasy());
    restaurant.setPhone(restaurantDTO.phone());
    restaurant.setUrlBanner(restaurantDTO.urlBanner());
    restaurant.setUrlLogo(restaurantDTO.urlLogo());
    restaurant.setDescription(restaurantDTO.description());

    Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
    return convertToDTOUpdate(updatedRestaurant);
  }

  @Transactional
  public void deleteRestaurant(Long restaurantId) {
    if (!restaurantRepository.existsById(restaurantId)) {
      throw new NotFoundException();
    }
    restaurantRepository.deleteById(restaurantId);
  }

  public RestaurantDTO convertToDTO(Restaurant restaurant) {
    return new RestaurantDTO(
        restaurant.getRestaurantId(),
        restaurant.getFantasy(),
        restaurant.getCnpj(),
        restaurant.getCategory().toString(),
        restaurant.getEmail(),
        restaurant.getPhone(),
        null, // Não expor a senha
        restaurant.getUrlBanner(),
        restaurant.getUrlLogo(),
        restaurant.getQuantityEvaluations(),
        restaurant.getTotalEvaluations(),
        restaurant.getAverageRating(),
        restaurant.getDescription(),
        null,
        restaurant.getDateCreation(),
        restaurant.getLastLogin()
    );
  }

  public RestaurantUpdateDTO convertToDTOUpdate(Restaurant restaurant) {
    return new RestaurantUpdateDTO(
        restaurant.getRestaurantId(),
        restaurant.getFantasy(),
        restaurant.getCnpj(),
        restaurant.getCategory().toString(),
        restaurant.getPhone(),
        restaurant.getUrlBanner(),
        restaurant.getUrlLogo(),
        restaurant.getDescription()
    );
  }

  public Restaurant convertToEntity(RestaurantDTO restaurantDTO) {
    Restaurant restaurant = new Restaurant();
    restaurant.setRestaurantId(restaurantDTO.restaurantId());
    restaurant.setCnpj(restaurantDTO.cnpj());
    restaurant.setFantasy(restaurantDTO.fantasy());
    restaurant.setEmail(restaurantDTO.email());
    restaurant.setPhone(restaurantDTO.phone());
    restaurant.setPassword(restaurantDTO.password());
    restaurant.setCategory(EnumRestaurantCategory.valueOf(restaurantDTO.category()));
    restaurant.setDateCreation(LocalDateTime.now());
    restaurant.setUrlBanner(restaurantDTO.urlBanner());
    restaurant.setUrlLogo(restaurantDTO.urlLogo());
    restaurant.setQuantityEvaluations(restaurantDTO.quantityEvaluations());
    restaurant.setDescription(restaurantDTO.description());
    restaurant.setTotalEvaluations(restaurantDTO.totalEvaluations());
    restaurant.setAverageRating(restaurantDTO.averageRating());
    return restaurant;
  }
}
