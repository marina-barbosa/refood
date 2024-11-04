package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser_UserId(Long userId); 

}
