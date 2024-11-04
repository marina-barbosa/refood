package com.projeto.ReFood.controller;

import com.projeto.ReFood.service.CardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.ReFood.dto.CardDTO;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

  @Autowired
  private CardService cardService;

  @Operation(
      summary = "Lista todos os cartões do usuário",
      description = "Retorna uma lista de todos os cartões do usuário.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Lista de cartões retornada com sucesso"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @GetMapping
  public ResponseEntity<List<CardDTO>> getAllCardsByUserId(@RequestHeader("Authorization") String token) {
    List<CardDTO> cards = cardService.getAllCardsByUserId(token);
    return ResponseEntity.ok(cards);
  }

  @Operation(
      summary = "Busca um cartão por ID",
      description = "Retorna os detalhes de um cartão específico com base no ID fornecido.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Cartão encontrado e retornado com sucesso"),
          @ApiResponse(responseCode = "404", description = "Cartão não encontrado")
      })
  @GetMapping("/{cardId}")
  public ResponseEntity<CardDTO> getCardById(@RequestHeader("Authorization") String token, @PathVariable Long cardId) {
    CardDTO cardDTO = cardService.getCardById(token, cardId);
    return ResponseEntity.ok(cardDTO);
  }

  @Operation(
      summary = "Cria um novo cartão para o usuário",
      description = "Permite a criação de um novo cartão ao usuário autenticado.",
      responses = {
          @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso"),
          @ApiResponse(responseCode = "400", description = "Requisição inválida"),
          @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
      })
  @PostMapping
  public ResponseEntity<CardDTO> createCard(@RequestHeader("Authorization") String token, @Valid @RequestBody CardDTO cardDTO) {
    CardDTO createdCard = cardService.createCard(token, cardDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{cardId}")
        .buildAndExpand(createdCard.cardId())
        .toUri();
    return ResponseEntity.created(location).body(createdCard);
  }

  @Operation(
      summary = "Atualiza um cartão específico do usuário",
      description = "Atualiza os detalhes de um cartão associado ao usuário autenticado, com base no token de autorização e no ID do endereço fornecidos..",
      responses = {
          @ApiResponse(responseCode = "200", description = "Cartão atualizado com sucesso"),
          @ApiResponse(responseCode = "404", description = "Cartão ou usuário não encontrado"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição"),
          @ApiResponse(responseCode = "401", description = "Token de autorização inválido ou não fornecido")
      })
  @PutMapping("/{cardId}")
  public ResponseEntity<CardDTO> updateCard(@RequestHeader("Authorization") String token, @PathVariable Long cardId, @Valid @RequestBody CardDTO cardDTO) {
    CardDTO updatedCard = cardService.updateCard(token, cardId, cardDTO);
    return ResponseEntity.ok(updatedCard);
  }

  @Operation(
      summary = "Exclui um cartão",
      description = "Remove o cartão especificado do usuário autenticado. O token de autorização deve ser fornecido no cabeçalho da requisição.",
      responses = {
          @ApiResponse(responseCode = "204", description = "Cartão excluído com sucesso"),
          @ApiResponse(responseCode = "404", description = "Cartão ou usuário não encontrado"),
          @ApiResponse(responseCode = "401", description = "Token de autorização inválido ou não fornecido")
      })
  @DeleteMapping("/{cardId}")
  public ResponseEntity<Void> deleteCard(@RequestHeader("Authorization") String token, @PathVariable Long cardId) {
    cardService.deleteCard(token, cardId);
    return ResponseEntity.noContent().build();
  }
}