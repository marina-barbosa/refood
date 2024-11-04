package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.Address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
  @Query("SELECT a FROM Address a WHERE a.user.id = :userId")
  List<Address> findAddressesByUserId(@Param("userId") Long userId);

  @Query("SELECT a FROM Address a WHERE a.restaurant.id = :restaurantId")
  List<Address> findAddressesByRestaurantId(@Param("restaurantId") Long userId);

  @Query("SELECT a FROM Address a WHERE a.id = :addressId AND a.user.id = :userId")
  Optional<Address> findByIdAndUserId(@Param("addressId") Long addressId, @Param("userId") Long userId);

  @Query("SELECT a FROM Address a WHERE a.id = :addressId AND a.restaurant.id = :restaurantId")
  Optional<Address> findByIdAndRestaurantId(@Param("addressId") Long addressId, @Param("restaurantId") Long userId);

  @Query("SELECT a FROM Address a WHERE a.user.id = :userId AND a.isStandard = true")
  Optional<Address> findByUserIdAndIsStandardTrue(@Param("userId") Long userId);

  @Query(value = "SELECT * FROM TB_ADDRESSES WHERE ADDRESS_TYPE = 'USER' AND USER_ID = :userId", nativeQuery = true)
  List<Address> nativeSearchAllByUserId(Long userId);

  @Query(value = "SELECT * FROM TB_ADDRESSES WHERE ADDRESS_TYPE = 'RESTAURANT' AND RESTAURANT_ID = :restaurantId", nativeQuery = true)
  List<Address> nativeSearchAllByRestaurantId(Long restaurantId);

  @Query(value = "SELECT * FROM TB_ADDRESSES WHERE ADDRESS_TYPE = 'USER' AND USER_ID = :userId AND ADDRESS_ID = :addressId", nativeQuery = true)
  Address nativeSearchByUserIdAndAddressId(Long userId, Long addressId);

  @Query(value = "SELECT * FROM TB_ADDRESSES WHERE ADDRESS_TYPE = 'RESTAURANT' AND RESTAURANT_ID = :restaurantId AND ADDRESS_ID = :addressId", nativeQuery = true)
  Address nativeSearchByRestaurantIdAndAddressId(Long restaurantId, Long addressId);
}
