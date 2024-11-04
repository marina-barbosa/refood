package com.projeto.ReFood.dto;

import com.projeto.ReFood.model.EnumProductCategory;
import com.projeto.ReFood.model.EnumRestaurantCategory;
import com.projeto.ReFood.model.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductRestaurantDTO {
  private Long productId;
  private String nameProduct;
  private String descriptionProduct;
  private String urlImgProduct;
  private float originalPrice;
  private float sellPrice;
  private Date expirationDate;
  private int quantity;
  private EnumProductCategory categoryProduct;
  private Date additionDate;
  private boolean active;
  private String restaurantName;
  private EnumRestaurantCategory category;

  public ProductRestaurantDTO(Product product, String restaurantName,EnumRestaurantCategory category) {
    this.productId=product.getProductId();
    this.nameProduct=product.getNameProduct();
    this.descriptionProduct=product.getDescriptionProduct();
    this.urlImgProduct=product.getUrlImgProduct();
    this.originalPrice=product.getOriginalPrice();
    this.sellPrice=product.getSellPrice();
    this.expirationDate=product.getExpirationDate();
    this.quantity=product.getQuantity();
    this.categoryProduct=product.getCategoryProduct();
    this.additionDate=product.getAdditionDate();
    this.active=product.isActive();
    this.restaurantName = restaurantName;
    this.category=category;
  }

}