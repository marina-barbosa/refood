package com.projeto.ReFood.controller;

import com.projeto.ReFood.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import com.projeto.ReFood.dto.AddressDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

  @Autowired
  private AddressService addressService;

  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<AddressDTO>> getAddressesByRestaurantId(@PathVariable Long restaurantId) {
      List<AddressDTO> addresses = addressService.getAddressesByRestaurantId(restaurantId);
      if (addresses.isEmpty()) {
          return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(addresses);
  }

  @Operation(
    summary = "Lista todos os endereços", 
    description = "Retorna uma lista de todos os endereços disponíveis no sistema.", 
    responses = {
      @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping
  public ResponseEntity<List<AddressDTO>> listAllAddresses() {
    List<AddressDTO> addresses = addressService.getAllAddresses();
    return ResponseEntity.ok(addresses);
  }

  @GetMapping("/user/all-addresses")
  public ResponseEntity<List<AddressDTO>> listUserAddresses() {
    List<AddressDTO> addresses = addressService.getAllUserAddresses();
    return ResponseEntity.ok(addresses);
  }

  @Operation(
    summary = "Busca um endereço por ID", 
    description = "Retorna os detalhes de um endereço específico com base no ID fornecido.", 
    responses = {
      @ApiResponse(responseCode = "200", description = "Endereço encontrado e retornado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
  })
  @GetMapping("/{addressId}")
  public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
    AddressDTO addressDTO = addressService.getAddressById(addressId);
    return ResponseEntity.ok(addressDTO);
  }

  @GetMapping("/user/{addressId}")
  public ResponseEntity<AddressDTO> getUserAddressById(@PathVariable Long addressId) {
    AddressDTO addressDTO = addressService.getUserAddressById(addressId);
    return ResponseEntity.ok(addressDTO);
  }

  @Operation(
    summary = "Cria um novo endereço para o usuário", 
    description = "Permite a criação de um novo endereço associado ao usuário autenticado. O token de autorização deve ser fornecido no cabeçalho da requisição.", 
    responses = {
      @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso"),
      @ApiResponse(responseCode = "400", description = "Requisição inválida"),
      @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
  })
  @PostMapping
  public ResponseEntity<AddressDTO> createAddress(@RequestHeader("Authorization") String token,
      @Valid @RequestBody AddressDTO addressDTO) {
    AddressDTO createdAddress = addressService.createAddress(addressDTO, token, null, null, null);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{addressId}")
        .buildAndExpand(createdAddress.addressId())
        .toUri();
    return ResponseEntity.created(location).body(createdAddress);
  }

  @Operation(
    summary = "Atualiza um endereço específico do usuário", 
    description = "Atualiza os detalhes de um endereço associado ao usuário autenticado, com base no token de autorização e no ID do endereço fornecidos. O corpo da requisição deve conter as novas informações do endereço.", 
    responses = {
      @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Endereço ou usuário não encontrado"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição"),
      @ApiResponse(responseCode = "401", description = "Token de autorização inválido ou não fornecido")
  })
  @PutMapping("/{addressId}")
  public ResponseEntity<AddressDTO> updateAddress(@RequestHeader("Authorization") String token,
      @PathVariable Long addressId, @Valid @RequestBody AddressDTO addressDTO)
      throws NotFoundException {
    AddressDTO updatedAddress = addressService.updateAddress(token, addressId, addressDTO);
    return ResponseEntity.ok(updatedAddress);
  }

  @Operation(
    summary = "Atualiza parcialmente um endereço para definir como padrão", 
    description = "Atualiza o endereço especificado como o endereço padrão do usuário autenticado. O token de autorização deve ser fornecido no cabeçalho da requisição.", 
    responses = {
      @ApiResponse(responseCode = "204", description = "Endereço atualizado com sucesso para padrão"),
      @ApiResponse(responseCode = "404", description = "Endereço ou usuário não encontrado"),
      @ApiResponse(responseCode = "401", description = "Token de autorização inválido ou não fornecido")
  })
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_RESTAURANT')")
  @PatchMapping("/{addressId}")
  public ResponseEntity<Void> updatePartialAddress(@RequestHeader("Authorization") String token,
      @PathVariable Long addressId)
      throws NotFoundException {
    addressService.updatePartialAddress(token, addressId);
    return ResponseEntity.noContent().build();
  }

  @Operation(
    summary = "Exclui um endereço", 
    description = "Remove o endereço especificado do usuário autenticado. O token de autorização deve ser fornecido no cabeçalho da requisição.", 
    responses = {
      @ApiResponse(responseCode = "204", description = "Endereço excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Endereço ou usuário não encontrado"),
      @ApiResponse(responseCode = "401", description = "Token de autorização inválido ou não fornecido")
  })
  @DeleteMapping("/{addressId}")
  public ResponseEntity<Void> deleteAddress(@RequestHeader("Authorization") String token,
      @PathVariable Long addressId) {
    addressService.deleteAddress(token, addressId);
    return ResponseEntity.noContent().build();
  }

  @Operation(
    summary = "Busca endereços por ID de usuário", 
    description = "Retorna uma lista de endereços associados ao ID do usuário baseado no token de autorização fornecido.", 
    responses = {
      @ApiResponse(responseCode = "200", description = "Endereços retornados com sucesso"),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
  })
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_RESTAURANT')")
  @GetMapping("/user")
  public ResponseEntity<List<AddressDTO>> getAddressByUserId(@RequestHeader("Authorization") String token)
      throws NotFoundException {
    List<AddressDTO> addressesDTO = addressService.getAddressesByUserId(token);
    return ResponseEntity.ok(addressesDTO);
  }

  @Operation(
    summary = "Obtém um endereço específico do usuário", 
    description = "Retorna os detalhes de um endereço específico associado ao usuário autenticado, com base no token de autorização fornecido e no ID do endereço.", 
    responses = {
      @ApiResponse(responseCode = "200", description = "Endereço retornado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Endereço ou usuário não encontrado"),
      @ApiResponse(responseCode = "401", description = "Token de autorização inválido ou não fornecido")
  })
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_RESTAURANT')")
  @GetMapping("/me/{addressId}")
  public ResponseEntity<AddressDTO> getAddressByUserToken(@RequestHeader("Authorization") String token,
      @PathVariable Long addressId) throws NotFoundException {
    AddressDTO addressDTO = addressService.getAddressByUserIdAndToken(token, addressId);
    return ResponseEntity.ok(addressDTO);
  }

  @Operation(
    summary = "Obtém o endereço padrão do usuário", 
    description = "Retorna o endereço padrão associado ao usuário autenticado, usando o token de autorização fornecido. O acesso a este endpoint é restrito a usuários com a função ROLE_USER.")
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_RESTAURANT')")
  @GetMapping("/default")
  public ResponseEntity<AddressDTO> getAddressDefault(@RequestHeader("Authorization") String token)
      throws NotFoundException {
    AddressDTO addressesDTO = addressService.getAddressDefault(token);
    return ResponseEntity.ok(addressesDTO);
  }

}