package com.projeto.ReFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projeto.ReFood.dto.GoogleDTO;
import com.projeto.ReFood.dto.LoginRequest;
import com.projeto.ReFood.dto.LoginResponse;
import com.projeto.ReFood.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("user/login")
  public ResponseEntity<LoginResponse> createUserAuthenticationToken(@RequestBody LoginRequest loginRequest) {
    return authService.authenticateUser(loginRequest.email(), loginRequest.password());
  }
  
  @PostMapping("restaurant/login")
  public ResponseEntity<LoginResponse> createRestaurantAuthenticationToken(@RequestBody LoginRequest loginRequest) {
    return authService.authenticateRestaurant(loginRequest.email(), loginRequest.password());
  }

  @PostMapping("/google/success")
  public ResponseEntity<?> loginSuccess(@RequestBody GoogleDTO googleDTO) {
    return authService.handleGoogleLoginSuccess(googleDTO);
  }
}
