package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.HistoricalOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoricalOrderRepository extends JpaRepository<HistoricalOrder, Long> {
    @Query("select h from HistoricalOrder h where h.restaurant.restaurantId = ?1")
    List<HistoricalOrder> findAllByRestaurantId(Long restaurantId);
}
