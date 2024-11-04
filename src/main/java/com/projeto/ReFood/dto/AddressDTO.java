package com.projeto.ReFood.dto;

import com.projeto.ReFood.model.EnumAddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record AddressDTO(
        Long addressId,

        @NotBlank(message = "O CEP é obrigatório.")
    String cep,

        @NotBlank(message = "O estado é obrigatório.")
        @Size(min = 2, max = 2, message = "O estado deve ter exatamente 2 letras.")
        String state,

        @NotBlank(message = "A cidade é obrigatória.")
        @Size(min = 2, message = "A cidade deve ter no mínimo 2 letras.")
        String city,

        @Size(min = 2, message = "O tipo deve ter no mínimo 2 letras.")
        String type,

        @NotBlank(message = "O bairro é obrigatório.")
        String district,

        @NotBlank(message = "A rua é obrigatória.")
        String street,

        @NotBlank(message = "O número é obrigatório.")
        String number,

        String complement, // opcional

        @NotNull(message = "O tipo de endereço é obrigatório.") @NotNull(message = "O tipo de endereço é obrigatório.")
        EnumAddressType addressType,

        boolean isStandard, // default = false

        Long userId,
        Long restaurantId,
        Long associatedOrderId
) {
//    public static AddressDTO fromEntity(Address address) {
//        return new AddressDTO(
//                address.getAddressId(),
//                address.getCep(),
//                address.getState(),
//                address.getCity(),
//                address.getType(),
//                address.getDistrict(),
//                address.getStreet(),
//                address.getNumber(),
//                address.getComplement(),
//                address.getAddressType(),
//                address.isStandard(),
//                address.getUser() != null ? address.getUser().getUserId() : null,
//                address.getRestaurant() != null ? address.getRestaurant().getRestaurantId() : null,
//                address.getAssociatedOrder() != null ? address.getAssociatedOrder().getOrderId() : null);
//    }
}