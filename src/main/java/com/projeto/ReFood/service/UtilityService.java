package com.projeto.ReFood.service;

import java.util.function.Consumer;

import com.projeto.ReFood.dto.AddressDTO;
import com.projeto.ReFood.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.repository.AddressRepository;
import com.projeto.ReFood.repository.CardRepository;
import com.projeto.ReFood.repository.CartRepository;
import com.projeto.ReFood.repository.OrderRepository;
import com.projeto.ReFood.repository.ProductRepository;
import com.projeto.ReFood.repository.RestaurantRepository;
import com.projeto.ReFood.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilityService {

  private final UserRepository userRepository;
  private final RestaurantRepository restaurantRepository;
  private final OrderRepository orderRepository;
  private final AddressRepository addressRepository;
  private final CardRepository cardRepository;
  private final ProductRepository productRepository;
  private final CartRepository cartRepository;

    public boolean isEmailUnique(String email) {
        return !userRepository.existsByEmail(email) && !restaurantRepository.existsByEmail(email);
    }

    public void associateUser(Consumer<User> userSetter, Long userId) {
        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException());
            userSetter.accept(user);
        } else {
            throw new IllegalArgumentException("User ID não pode ser nulo ao criar um card.");
        }
    }

    public void associateRestaurant(Consumer<Restaurant> restaurantSetter, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException());
        restaurantSetter.accept(restaurant);
    }

    public void associateOrder(HistoricalOrder historicalOrder, Long orderId) {
        if (orderId != null) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new NotFoundException());
            historicalOrder.setAssociatedHistoricalOrder(order);
        } else {
            throw new IllegalArgumentException("Order ID não pode ser nulo ao criar um historical order.");
        }
    }

    public void associateAddress(Consumer<Address> addressSetter, Long addressId) {
        if (addressId != null) {
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new NotFoundException());
            addressSetter.accept(address);
        } else {
            throw new IllegalArgumentException("Address ID não pode ser nulo ao associar um address.");
        }
    }

    public void associateOrder(Consumer<Order> orderSetter, Long orderId) {
        if (orderId != null) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new NotFoundException());
            orderSetter.accept(order);
        } else {
            throw new IllegalArgumentException("Order ID não pode ser nulo ao associar um order.");
        }
    }

    public void associateCard(Consumer<Card> cardSetter, Long cardId) {
        if (cardId != null) {
            Card card = cardRepository.findById(cardId)
                    .orElseThrow(() -> new NotFoundException());
            cardSetter.accept(card);
        } else {
            throw new IllegalArgumentException("Card ID não pode ser nulo ao associar um card.");
        }
    }

    public void associateProduct(Consumer<Product> productSetter, Long productId) {
        if (productId != null) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException());
            productSetter.accept(product);
        } else {
            throw new IllegalArgumentException("Product ID não pode ser nulo ao associar um product.");
        }
    }

    public void associateCart(Consumer<Cart> cartSetter, Long cartId) {
        if (cartId != null) {
            Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new NotFoundException());
            cartSetter.accept(cart);
        } else {
            throw new IllegalArgumentException("Cart ID não pode ser nulo ao associar um cart.");
        }
    }

    public void addAddressToRestaurant(Restaurant restaurant, @NotNull(message = "Obrigatório") @Valid() AddressDTO addressDTO) {
        Address address = convertAddressDTOToEntity(addressDTO, null, restaurant, null);

        addressRepository.save(address);
    }

    public Address convertAddressDTOToEntity(AddressDTO addressDTO, User user, Restaurant restaurant, Order order) {
        Address address = new Address();
        address.setAddressId(addressDTO.addressId());
        address.setCep(addressDTO.cep());
        address.setState(addressDTO.state());
        address.setCity(addressDTO.city());
        address.setType(addressDTO.type());
        address.setDistrict(addressDTO.district());
        address.setStreet(addressDTO.street());
        address.setNumber(addressDTO.number());
        address.setComplement(addressDTO.complement());
        address.setAddressType(EnumAddressType.valueOf(addressDTO.addressType().toString()));
        address.setStandard(addressDTO.isStandard());

        if (user != null) {
            address.setUser(user);
        }
        if (restaurant != null) {
            address.setRestaurant(restaurant);
        }
        if (order != null) {
            address.setAssociatedOrder(order);
        }
        return address;
    }
}
