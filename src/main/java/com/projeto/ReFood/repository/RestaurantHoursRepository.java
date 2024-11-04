package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.EnumDayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.ReFood.model.RestaurantHours;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantHoursRepository extends JpaRepository<RestaurantHours, Long> {
    List<RestaurantHours> findByDayOfWeek(EnumDayOfWeek dayOfWeek);

    @Query("SELECT rh FROM RestaurantHours rh WHERE rh.restaurant.id = :restaurantId")
    List<RestaurantHours> findByRestaurantId(@Param("restaurantId") Long restaurantId);
}
