package com.projeto.ReFood.controller;

import com.projeto.ReFood.dto.RestaurantDTO;
import com.projeto.ReFood.dto.UserDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler;
import com.projeto.ReFood.security.JwtTokenProvider;
import com.projeto.ReFood.service.RestaurantService;
import com.projeto.ReFood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/token")
public class TokenController {

  @Autowired
  private UserService userService;

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @GetMapping("/info")
  public ResponseEntity<?> getTokenInfo(@RequestHeader("Authorization") String token) {
    if (token.startsWith("Bearer ")) {
      token = token.substring(7).trim();
    }
    @SuppressWarnings("unchecked")
    ArrayList<String> roles = jwtTokenProvider.extractClaim(token, claims -> claims.get("roles", ArrayList.class));
    String role = roles.get(0);
    if (role.equals("ROLE_USER")) {
      try {
        UserDTO userDTO = userService.getUserInfoByToken(token);
        return ResponseEntity.ok(userDTO);
      } catch (InvalidBearerTokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
      } catch (GlobalExceptionHandler.NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
      }
    } else if (role.equals("ROLE_RESTAURANT")) {
      try {
        RestaurantDTO restaurantDTO = restaurantService.getRestaurantInfoByToken(token);
        return ResponseEntity.ok(restaurantDTO);
      } catch (InvalidBearerTokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Restaurante não autenticado");
      } catch (GlobalExceptionHandler.NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurante não encontrado");
      }
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário ou restaurante não encontrado");
  }

}
