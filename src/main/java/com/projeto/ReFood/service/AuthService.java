package com.projeto.ReFood.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.projeto.ReFood.dto.GoogleDTO;
import com.projeto.ReFood.dto.LoginResponse;
import com.projeto.ReFood.exception.BadRequestException;
import com.projeto.ReFood.exception.GlobalExceptionHandler.BadCredentialsException;
import com.projeto.ReFood.exception.GlobalExceptionHandler.InternalServerErrorException;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.CustomUserDetails;
import com.projeto.ReFood.model.Restaurant;
import com.projeto.ReFood.model.User;
import com.projeto.ReFood.model.UserInfo;
import com.projeto.ReFood.repository.RestaurantRepository;
import com.projeto.ReFood.repository.UserRepository;
import com.projeto.ReFood.security.JwtTokenProvider;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserDetailsService userDetailsService;
  private final UtilityService utilityService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final RestaurantRepository restaurantRepository;

  @Autowired
  public AuthService(AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider,
      CustomUserDetailsService userDetailsService,
      UtilityService utilityService,
      PasswordEncoder passwordEncoder,
      UserRepository userRepository,
      RestaurantRepository restaurantRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.userDetailsService = userDetailsService;
    this.utilityService = utilityService;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.restaurantRepository = restaurantRepository;
  }

  private boolean updateLastLoginForUserOrRestaurant(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      user.setLastLogin(LocalDateTime.now());
      userRepository.save(user);
      return true;
    }

    Optional<Restaurant> optionalRestaurant = restaurantRepository.findByEmail(email);
    if (optionalRestaurant.isPresent()) {
      Restaurant restaurant = optionalRestaurant.get();
      restaurant.setLastLogin(LocalDateTime.now());
      restaurantRepository.save(restaurant);
      return true;
    }

    return false;
  }

  public ResponseEntity<LoginResponse> authenticateUser(String email, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException();
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }

    boolean isUserUpdated = updateLastLoginForUserOrRestaurant(email);

    if (!isUserUpdated) {
      throw new NotFoundException();
    }

    CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

    String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();
    if (!role.equals("ROLE_USER")) {
      throw new BadRequestException("Essa conta não é de usuário");
    }

    String jwt = jwtTokenProvider.generateToken(userDetails, userDetails.getId());

    return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getId(),
        userDetails.getNome(), email));
  }

  public ResponseEntity<LoginResponse> authenticateRestaurant(String email, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException();
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }

    boolean isUserUpdated = updateLastLoginForUserOrRestaurant(email);

    if (!isUserUpdated) {
      throw new NotFoundException();
    }

    CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

    String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();
    if (!role.equals("ROLE_RESTAURANT")) {
      throw new BadRequestException("Essa conta não é de restaurante");
    }
    
    String jwt = jwtTokenProvider.generateToken(userDetails, userDetails.getId());

    return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getId(),
        userDetails.getNome(), email));
  }

  public UserInfo getCurrentUserInfo() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    UserInfo currentUserInfo = new UserInfo(null, null);

    if (authentication != null && authentication.isAuthenticated() &&
        !authentication.getPrincipal().equals("anonymousUser")) {

      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
      currentUserInfo = new UserInfo(userDetails.getId(),
          userDetails.getAuthorities().stream().findFirst().get().getAuthority());
    }

    return currentUserInfo;
  }

  public ResponseEntity<?> handleGoogleLoginSuccess(GoogleDTO googleDTO) {

    String email = googleDTO.email();
    String password = googleDTO.sub();

    if (!utilityService.isEmailUnique(email)) {
      return authenticateUser(email, password);
    }

    return createNewGoogleUser(googleDTO);
  }

  private ResponseEntity<?> createNewGoogleUser(GoogleDTO googleDTO) {
    User user = new User();
    user.setName(googleDTO.name());
    user.setEmail(googleDTO.email());
    String password = googleDTO.sub();
    user.setPassword(passwordEncoder.encode(password));
    user.setDateCreation(LocalDateTime.now());
    user.setLastLogin(LocalDateTime.now());
    User userResult = userRepository.save(user);

    CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(googleDTO.email());
    String jwt = jwtTokenProvider.generateToken(userDetails, userResult.getUserId());

    return ResponseEntity
        .ok(new LoginResponse(jwt, userDetails.getId(), userDetails.getNome(), userDetails.getEmail()));
  }
}
