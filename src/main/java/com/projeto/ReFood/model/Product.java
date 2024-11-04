package com.projeto.ReFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "tb_products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long productId;

  @NotBlank(message = "O nome do produto não pode estar vazio.")
  @Column(name = "name_product", nullable = false)
  private String nameProduct;

  @Column(name = "description_product")
  private String descriptionProduct;

  @Column(name = "url_img_product")
  private String urlImgProduct;

  @NotNull(message = "O valor do produto não pode ser nulo.")
  @Column(name = "original_price", nullable = false)
  private float originalPrice;

  @NotNull(message = "O valor de venda não pode ser nulo.")
  @Column(name = "sell_price", nullable = false)
  private float sellPrice;

  @NotNull(message = "A data de expiração não pode ser nula.")
  @Column(name = "expiration_date", nullable = false)
  private Date expirationDate;

  @NotNull(message = "A quantidade não pode ser nula.")
  @Min(value = 0, message = "A quantidade não pode ser negativa.")
  @Column(name = "quantity", nullable = false)
  private int quantity;

  @NotNull(message = "A categoria do produto não pode ser nula.")
  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private EnumProductCategory categoryProduct;

  @NotNull(message = "A data de adição não pode ser nula.")
  @Column(name = "addition_date", nullable = false)
  private Date additionDate;

  @NotNull(message = "O status ativo deve ser especificado.")
  @Column(name = "active", nullable = false)
  private boolean active;

  @NotNull(message = "O restaurante não pode ser nulo.")
  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;


}
