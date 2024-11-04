package com.projeto.ReFood.repository;

import com.projeto.ReFood.dto.ProductRestaurantDTO;
import com.projeto.ReFood.dto.RestaurantInfoDTO;
import com.projeto.ReFood.model.EnumProductCategory;
import com.projeto.ReFood.model.EnumRestaurantCategory;
import com.projeto.ReFood.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findByRestaurant_RestaurantId(Long restaurantId);

  @Query("SELECT r.fantasy FROM Product p JOIN p.restaurant r WHERE p.productId = :productId")
  String findRestaurantNameByProductId(Long productId);

  @Query("SELECT new com.projeto.ReFood.dto.ProductRestaurantDTO(p, r.fantasy, r.category) " +
      "FROM Product p " +
      "JOIN p.restaurant r " +
      "WHERE p.active = true " +
      "AND (:produto IS NULL OR " +
      "     LOWER(p.nameProduct) LIKE LOWER(CONCAT('%', :produto, '%')) " +
      "     OR LOWER(p.descriptionProduct) LIKE LOWER(CONCAT('%', :produto, '%')) " +
      "     OR LOWER(r.fantasy) LIKE LOWER(CONCAT('%', :produto, '%'))) " +
      "AND (:tipos IS NULL OR r.category IN :tipos) " +
      "AND (:categorias IS NULL OR p.categoryProduct IN :categorias) " +
      "AND (:preco IS NULL OR p.sellPrice <= :preco) " +
      "AND (p.quantity>0) " +
      "ORDER BY p.nameProduct ASC")
  Page<ProductRestaurantDTO> findProductsByFilters(@Param("produto") String produto,
                                                   @Param("tipos") List<EnumRestaurantCategory> tipos,
                                                   @Param("categorias") List<EnumProductCategory> categorias,
                                                   @Param("preco") Float preco,
                                                   Pageable pageable);

  @Query("""
          SELECT new com.projeto.ReFood.dto.RestaurantInfoDTO(
              r.restaurantId,
              r.fantasy,
              r.email,
              r.dateCreation,
              r.category,
              r.urlBanner,
              r.urlLogo,
              r.quantityEvaluations,
              r.totalEvaluations,
              r.phone,
              r.description,
              r.averageRating
          )
          FROM Product p
          JOIN p.restaurant r
          WHERE p.productId = :productId
      """)
  RestaurantInfoDTO findRestaurantInfoByProductId(Long productId);

}
