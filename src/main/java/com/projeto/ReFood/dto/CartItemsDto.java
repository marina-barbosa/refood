package com.projeto.ReFood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsDto {
  private Long cartId;
  private Long productId;
  private String nameProduct;
  private String descriptionProduct;
  private int quantity;
  private float unitValue;
  private float subtotal;

}