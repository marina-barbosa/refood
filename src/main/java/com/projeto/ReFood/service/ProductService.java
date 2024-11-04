package com.projeto.ReFood.service;

import com.projeto.ReFood.dto.ProductDTO;
import com.projeto.ReFood.dto.ProductPartialUpdateDTO;
import com.projeto.ReFood.dto.ProductRestaurantDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.EnumProductCategory;
import com.projeto.ReFood.model.EnumRestaurantCategory;
import com.projeto.ReFood.model.Product;
import com.projeto.ReFood.dto.RestaurantInfoDTO;
import com.projeto.ReFood.repository.ProductRepository;
import com.projeto.ReFood.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UtilityService utilityService;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Transactional(readOnly = true)
  public RestaurantInfoDTO getRestaurantInfoByProductId(Long productId) {
    return productRepository.findRestaurantInfoByProductId(productId);
  }

  @Transactional(readOnly = true)
  public String getRestaurantNameByProductId(Long productId) {
    return productRepository.findRestaurantNameByProductId(productId);
  }

  @Transactional(readOnly = true)
  public List<ProductDTO> getAllProducts() {
    return productRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ProductDTO getProductById(Long productId) {
    return productRepository.findById(productId)
        .map(this::convertToDTO)
        .orElseThrow(NotFoundException::new);
  }


@Transactional(readOnly = true)
public Page<ProductRestaurantDTO> getFilteredProducts(String product, String types, String categories, String price, Integer currentPage) {
  Integer pageSize = 20; // Número de registros por página
  Pageable pageable = PageRequest.of(currentPage, pageSize); // Cria o objeto Pageable

  Float convertedPrice = null;
  if (price != null && !price.isEmpty()) {
    convertedPrice = Float.parseFloat(price);
  }

  List<EnumRestaurantCategory> typeList = (types != null && !types.isEmpty()) ?
      Arrays.stream(types.split(" "))
          .map(EnumRestaurantCategory::valueOf)
          .collect(Collectors.toList()) : null;

  List<EnumProductCategory> categoryList = (categories != null && !categories.isEmpty()) ?
      Arrays.stream(categories.split(" "))
          .map(EnumProductCategory::valueOf)
          .collect(Collectors.toList()) : null;

  return productRepository.findProductsByFilters(product, typeList, categoryList, convertedPrice, pageable);
}

  @Transactional
  public ProductDTO createProduct(@Valid ProductDTO productDTO, String token) {

    Long restaurantId = jwtTokenProvider.extractUserId(token);
    productDTO = new ProductDTO(
        productDTO.productId(),
        productDTO.nameProd(),
        productDTO.descriptionProd(),
        productDTO.urlImgProd(),
        productDTO.originalPrice(),
        productDTO.sellPrice(),
        productDTO.expirationDate(),
        productDTO.quantity(),
        productDTO.categoryProduct(),
        productDTO.additionDate(),
        productDTO.active(),
        restaurantId);

    Product product = convertToEntity(productDTO);
    utilityService.associateRestaurant(product::setRestaurant, productDTO.restaurantId());
    product = productRepository.save(product);
    return convertToDTO(product);
  }

  @Transactional
  public ProductDTO updateProduct(Long productId, @Valid ProductDTO productDTO) {
    Product product = productRepository.findById(productId)
        .orElseThrow(NotFoundException::new);

    // Update all fields with BeanUtils
    BeanUtils.copyProperties(productDTO, product, getNullPropertyNames(productDTO));
    utilityService.associateRestaurant(product::setRestaurant, productDTO.restaurantId());

    product = productRepository.save(product);
    return convertToDTO(product);
  }

  @Transactional
  public void deleteProduct(Long productId) {
    if (!productRepository.existsById(productId)) {
      throw new NotFoundException();
    }
    productRepository.deleteById(productId);
  }

  private String[] getNullPropertyNames(ProductDTO productDTO) {
    return Arrays.stream(BeanUtils.getPropertyDescriptors(ProductDTO.class))
        .map(pd -> pd.getName())
        .filter(name -> {
          try {
            return BeanUtils.getPropertyDescriptor(ProductDTO.class, name)
                .getReadMethod().invoke(productDTO) == null;
          } catch (Exception e) {
            return false;
          }
        }).toArray(String[]::new);
  }

  private ProductDTO convertToDTO(Product product) {
    return new ProductDTO(
        product.getProductId(),
        product.getNameProduct(),
        product.getDescriptionProduct(),
        product.getUrlImgProduct(),
        product.getOriginalPrice(),
        product.getSellPrice(),
        product.getExpirationDate(),
        product.getQuantity(),
        product.getCategoryProduct(),
        product.getAdditionDate(),
        product.isActive(),
        product.getRestaurant().getRestaurantId());
  }

  private Product convertToEntity(ProductDTO productDTO) {
    Product product = new Product();
    product.setProductId(productDTO.productId());
    product.setNameProduct(productDTO.nameProd());
    product.setDescriptionProduct(productDTO.descriptionProd());
    product.setUrlImgProduct(productDTO.urlImgProd());
    product.setOriginalPrice(productDTO.originalPrice());
    product.setSellPrice(productDTO.sellPrice());
    product.setQuantity(productDTO.quantity());
    product.setExpirationDate(productDTO.expirationDate());
    product.setCategoryProduct(productDTO.categoryProduct());
    product.setAdditionDate(productDTO.additionDate());
    product.setActive(productDTO.active());
    utilityService.associateRestaurant(product::setRestaurant, productDTO.restaurantId());
    return product;
  }

  @Transactional
  public ProductDTO partialUpdateProduct(Long productId, @Valid ProductPartialUpdateDTO productPartialUpdateDTO) {
    Product product = productRepository.findById(productId)
        .orElseThrow(NotFoundException::new);

    if (productPartialUpdateDTO.nameProd() != null) {
      product.setNameProduct(productPartialUpdateDTO.nameProd());
    }
    if (productPartialUpdateDTO.descriptionProd() != null) {
      product.setDescriptionProduct(productPartialUpdateDTO.descriptionProd());
    }
    if (productPartialUpdateDTO.urlImgProd() != null) {
      product.setUrlImgProduct(productPartialUpdateDTO.urlImgProd());
    }
    if (productPartialUpdateDTO.originalPrice() != null) {
      product.setOriginalPrice(productPartialUpdateDTO.originalPrice());
    }
    if (productPartialUpdateDTO.sellPrice() != null) {
      product.setSellPrice(productPartialUpdateDTO.sellPrice());
    }
    if (productPartialUpdateDTO.expirationDate() != null) {
      product.setExpirationDate(productPartialUpdateDTO.expirationDate());
    }
    if (productPartialUpdateDTO.quantity() != null) {
      product.setQuantity(productPartialUpdateDTO.quantity());
    }
    if (productPartialUpdateDTO.categoryProduct() != null) {
      product.setCategoryProduct(productPartialUpdateDTO.categoryProduct());
    }
    if (productPartialUpdateDTO.active() != null) {
      product.setActive(productPartialUpdateDTO.active());
    }

    product = productRepository.save(product);
    return convertToDTO(product);
  }

  @Transactional(readOnly = true)
  public List<ProductDTO> getProductsByRestaurantId(String token) {
    Long restaurantId = jwtTokenProvider.extractUserId(token);
    return productRepository.findByRestaurant_RestaurantId(restaurantId).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

}
