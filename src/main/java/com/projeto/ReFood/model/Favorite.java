package com.projeto.ReFood.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "tb_favorites")
public class Favorite {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "favorite_id")
  private Long favoriteId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;
}
