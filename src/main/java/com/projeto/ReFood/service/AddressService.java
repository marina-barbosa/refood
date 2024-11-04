package com.projeto.ReFood.service;

import com.projeto.ReFood.dto.AddressDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.ForbiddenException;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.repository.AddressRepository;
import com.projeto.ReFood.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.model.Address;
import com.projeto.ReFood.model.EnumAddressType;
import com.projeto.ReFood.model.Order;
import com.projeto.ReFood.model.Restaurant;
import com.projeto.ReFood.model.User;
import com.projeto.ReFood.model.UserInfo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class AddressService {

  private final AddressRepository addressRepository;
  private final AuthService authService;
  private final UtilityService utilityService;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional(readOnly = true)
  public List<AddressDTO> getAddressesByRestaurantId(Long restaurantId) {
      List<Address> addresses = addressRepository.findAddressesByRestaurantId(restaurantId);
      return addresses.stream()
              .map(this::convertToDTO)
              .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<AddressDTO> getAllAddresses() {
    return addressRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<AddressDTO> getAddressesByUserId(String token) {
    Long id = jwtTokenProvider.extractUserId(token);
    String role = jwtTokenProvider.extractUserRoles(token).toString();
    if(Objects.equals(role, "[ROLE_USER]")) {
      return addressRepository
              .findAddressesByUserId(id)
              .stream()
              .map(this::convertToDTO)
              .collect(Collectors.toList());
    }
    if(Objects.equals(role, "[ROLE_RESTAURANT]")) {
      return addressRepository
              .findAddressesByRestaurantId(id)
              .stream()
              .map(this::convertToDTO)
              .collect(Collectors.toList());
    }
    return null;
  }

  @Transactional(readOnly = true)
  public AddressDTO getAddressByUserIdAndToken(String token, long addressId) {
    Long id = jwtTokenProvider.extractUserId(token);

    Address address = addressRepository.findByIdAndUserId(addressId, id)
        .orElseThrow(() -> {
          return new NotFoundException();
        });
    return convertToDTO(address);
  }

  @Transactional(readOnly = true)
  public List<AddressDTO> getAllUserAddresses() {
    UserInfo currentUserInfo = authService.getCurrentUserInfo();

    if (currentUserInfo.getId() == null) {
      throw new ForbiddenException();
    }

    List<Address> addresses;

    if (currentUserInfo.getRole().equals("ROLE_USER")) {
      addresses = addressRepository.nativeSearchAllByUserId(currentUserInfo.getId());

    } else if (currentUserInfo.getRole().equals("ROLE_RESTAURANT")) {
      addresses = addressRepository.nativeSearchAllByRestaurantId(currentUserInfo.getId());

    } else {
      throw new ForbiddenException();
    }

    List<AddressDTO> addressDTOs = addresses.stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());

    return addressDTOs;
  }

  @Transactional(readOnly = true)
  public AddressDTO getAddressById(Long addressId) {
    Address address = addressRepository.findById(addressId)
        .orElseThrow(() -> {
          return new NotFoundException();
        });
    return convertToDTO(address);
  }

  @Transactional(readOnly = true)
  public AddressDTO getUserAddressById(Long addressId) {
    UserInfo currentUserInfo = authService.getCurrentUserInfo();

    if (currentUserInfo.getId() == null) {
      throw new ForbiddenException();
    }

    Address address;

    if (currentUserInfo.getRole().equals("ROLE_USER")) {
      address = addressRepository
          .nativeSearchByUserIdAndAddressId(currentUserInfo.getId(), addressId);

    } else if (currentUserInfo.getRole().equals("ROLE_RESTAURANT")) {
      address = addressRepository
          .nativeSearchByRestaurantIdAndAddressId(currentUserInfo.getId(), addressId);

    } else {
      throw new ForbiddenException();
    }

    if (address == null) {
      throw new NotFoundException();
    }

    AddressDTO addressDTO;
    addressDTO = convertToDTO(address);

    return addressDTO;
  }

  @Transactional
  public AddressDTO createAddress(@Valid AddressDTO addressDTO, String token, User user, Restaurant restaurant,
      Order order) {
    Long id = jwtTokenProvider.extractUserId(token);
    String role = jwtTokenProvider.extractUserRoles(token).toString();
    Address address = new Address();

    address.setCep(addressDTO.cep());
    address.setState(addressDTO.state());
    address.setDistrict(addressDTO.district());
    address.setCity(addressDTO.city());
    address.setType(addressDTO.type());
    address.setStreet(addressDTO.street());
    address.setNumber(addressDTO.number());
    address.setStandard(false);
    address.setComplement(addressDTO.complement());
    address.setAddressType(EnumAddressType.valueOf(addressDTO.addressType().toString()));

    List<AddressDTO> addresses = getAddressesByUserId(token);
    if (addresses.isEmpty()) {
      address.setStandard(true);
    }

    if(Objects.equals(role, "[ROLE_USER]")) {
      if (addressDTO.userId() != null || id != null) {
        Long userId = addressDTO.userId();
        if (userId == null) {
          userId = id;
        }
        utilityService.associateUser(address::setUser, userId);
      }
    } else
      if(Objects.equals(role, "[ROLE_RESTAURANT]")) {
      if (addressDTO.restaurantId() != null || id != null) {
        Long restaurantId = addressDTO.restaurantId();
        if (restaurantId == null) {
          restaurantId = id;
        }
        utilityService.associateRestaurant(address::setRestaurant, restaurantId);
      }
    }
    if (user != null) {
      address.setUser(user);
    }
    if (restaurant != null) {
      address.setRestaurant(restaurant);
    }
    if (order != null) {
      address.setAssociatedOrder(order);
    }
    addressRepository.save(address);
    return convertToDTO(address);
  }

  @Transactional
  public AddressDTO updateAddress(String token, Long addressId, @Valid AddressDTO addressDTO) {
    Long id = jwtTokenProvider.extractUserId(token);
    String role = jwtTokenProvider.extractUserRoles(token).toString();
    Address address;
    if(Objects.equals(role, "[ROLE_USER]")) {
      address = addressRepository.findByIdAndUserId(addressId, id)
              .orElseThrow(() -> {
                return new NotFoundException();
              });
    } else {
      address = addressRepository.findByIdAndRestaurantId(addressId, id)
                .orElseThrow(() -> {
              return new NotFoundException();
            });
    }

    address.setStreet(addressDTO.street());
    address.setNumber(addressDTO.number());
    address.setDistrict(addressDTO.district());
    address.setType(addressDTO.type());
    address.setCity(addressDTO.city());
    address.setComplement(addressDTO.complement());
    address.setAddressType(EnumAddressType.valueOf(addressDTO.addressType().toString()));
    address.setCep(addressDTO.cep());
    address.setState(addressDTO.state());

    address = addressRepository.save(address);

    return convertToDTO(address);
  }

  @Transactional
  public void updatePartialAddress(String token, Long addressId) {
    Long userId = jwtTokenProvider.extractUserId(token);
    addressRepository.findByIdAndUserId(addressId, userId)
        .orElseThrow(() -> {
          return new NotFoundException();
        });

    List<Address> addressList = addressRepository.findAddressesByUserId(userId);

    addressList.forEach((a) -> {
      if (a.isStandard()) {
        a.setStandard(false);
      }
      if (a.getAddressId().equals(addressId)) {
        a.setStandard(true);
      }
    });

    addressRepository.saveAll(addressList);
  }

  @Transactional
  public AddressDTO getAddressDefault(String token) {
    Long userId = jwtTokenProvider.extractUserId(token);
    Address address = addressRepository.findByUserIdAndIsStandardTrue(userId)
        .orElse(null);

    return address != null ? convertToDTO(address) : null;
  }

  @Transactional
  public void deleteAddress(String token, Long addressId) {
    Long id = jwtTokenProvider.extractUserId(token);
    Optional<Address> address = addressRepository.findByIdAndUserId(addressId, id);

    if (!addressRepository.existsById(addressId)) {
      throw new NotFoundException();
    }
    addressRepository.deleteById(address.get().getAddressId());
  }

  public AddressDTO convertToDTO(Address address) {
    return new AddressDTO(
        address.getAddressId(),
        address.getCep(),
        address.getState(),
        address.getCity(),
        address.getType(),
        address.getDistrict(),
        address.getStreet(),
        address.getNumber(),
        address.getComplement(),
        address.getAddressType(),
        address.isStandard(),
        address.getUser() != null ? address.getUser().getUserId() : null,
        address.getRestaurant() != null ? address.getRestaurant().getRestaurantId() : null,
        address.getAssociatedOrder() != null ? address.getAssociatedOrder().getOrderId() : null);
  }

  public Address convertToEntity(AddressDTO addressDTO, User user, Restaurant restaurant, Order order) {
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