package com.projeto.ReFood.dto;

import com.projeto.ReFood.model.EnumProductCategory;


import java.util.Date;

public record ProductPartialUpdateDTO(
        String nameProd,
        String descriptionProd,
        String urlImgProd,
        Float originalPrice,
        Float sellPrice,
        Date expirationDate,
        Integer quantity,
        EnumProductCategory categoryProduct,
        Boolean active
) {}
