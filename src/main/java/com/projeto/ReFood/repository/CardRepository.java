package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

  @Query("SELECT c FROM Card c WHERE c.user.id = :userId")
  List<Card> findCardByUserId(@Param("userId") Long userId);

  @Query("SELECT c FROM Card c WHERE c.id = :cardId AND c.user.id = :userId")
  Optional<Card> findByIdAndUserId(@Param("cardId") Long addressId, @Param("userId") Long userId);

}
