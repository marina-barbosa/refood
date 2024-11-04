package com.projeto.ReFood.service;

import com.projeto.ReFood.dto.CartDTO;
import com.projeto.ReFood.dto.CartItemsDto;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.Cart;
import com.projeto.ReFood.model.CartItem;
import com.projeto.ReFood.model.CartItemPK;
import com.projeto.ReFood.repository.CartItemRepository;
import com.projeto.ReFood.repository.CartRepository;

import jakarta.persistence.Tuple;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class CartService {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private UtilityService utilityService;

  @Autowired
  private CartItemRepository cartItemRepository;

  @Transactional
  public void removeItemFromCart(Long cartId, Long productId) {
    CartItemPK cartItemPK = new CartItemPK(cartId, productId);
    CartItem cartItem = cartItemRepository.findById(cartItemPK)
        .orElseThrow(() -> new NotFoundException());

    if (cartItem.getQuantity() > 1) {
      cartItem.setQuantity(cartItem.getQuantity() - 1);
      cartItem.setSubtotal(cartItem.getQuantity() * cartItem.getUnitValue());
      cartItemRepository.save(cartItem);
    } else {
      cartItemRepository.deleteById(cartItemPK);
    }

    Cart cart = cartItem.getCart();
    float newTotalValue = (float) cart.getCartItems().stream()
        .mapToDouble(CartItem::getSubtotal)
        .sum();
    cart.setTotalValue(newTotalValue);
    cartRepository.save(cart);
  }

  @Transactional(readOnly = true)
  public List<CartItemsDto> getCartDetailsByUserId(Long userId) {
    List<Tuple> cartItemsTuples = cartRepository.getCartItemsByUserId(userId);

    if (cartItemsTuples.isEmpty()) {
      throw new NotFoundException();
    }

    List<CartItemsDto> cartItems = cartItemsTuples.stream()
        .map(tuple -> new CartItemsDto(
            tuple.get(0, Long.class), // cartId
            tuple.get(1, Long.class), // productId
            tuple.get(2, String.class), // nameProduct
            tuple.get(3, String.class), // descriptionProduct
            tuple.get(4, Integer.class), // quantity
            tuple.get(5, Float.class), // unitValue
            tuple.get(6, Float.class) // subtotal
        ))
        .collect(Collectors.toList());

    return cartItems;
  }

  @Transactional
  public void clearCart(Long cartId) {
    Cart cart = cartRepository.findById(cartId)
        .orElseThrow(() -> new NotFoundException());

    cart.getCartItems().clear();
    cart.setTotalValue(0);
    cartRepository.save(cart);
  }

  @Transactional(readOnly = true)
  public List<CartDTO> getAllCarts() {
    return cartRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public CartDTO getCartById(Long cartId) {
    return cartRepository.findById(cartId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public CartDTO createCart(@Valid CartDTO cartDTO) {
    Cart cart = convertToEntity(cartDTO);
    utilityService.associateUser(cart::setUser, cartDTO.userId());
    cart = cartRepository.save(cart);
    return convertToDTO(cart);
  }

  @Transactional
  public CartDTO updateCart(Long cartId, @Valid CartDTO cartDTO) {
    Cart cart = cartRepository.findById(cartId)
        .orElseThrow(() -> new NotFoundException());

    cart.setTotalValue(cartDTO.totalValue());

    utilityService.associateUser(cart::setUser, cartDTO.userId());

    cart = cartRepository.save(cart);
    return convertToDTO(cart);
  }

  @Transactional
  public void deleteCart(Long cartId) {
    if (!cartRepository.existsById(cartId)) {
      throw new NotFoundException();
    }
    cartRepository.deleteById(cartId);
  }

  private CartDTO convertToDTO(Cart cart) {
    return new CartDTO(
        cart.getCartId(),
        cart.getTotalValue(),
        cart.getUser().getUserId());
  }

  private Cart convertToEntity(CartDTO cartDTO) {
    Cart cart = new Cart();
    cart.setCartId(cartDTO.cartId());
    cart.setTotalValue(cartDTO.totalValue());
    utilityService.associateUser(cart::setUser, cartDTO.userId());
    return cart;
  }

}
