package com.projeto.ReFood.controller;

import com.projeto.ReFood.dto.ProductDTO;
import com.projeto.ReFood.dto.ProductPartialUpdateDTO;
import com.projeto.ReFood.dto.ProductRestaurantDTO;
import com.projeto.ReFood.dto.RestaurantInfoDTO;
import com.projeto.ReFood.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;

import java.io.File;
import java.net.URI;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("/{productId}/restaurant-info")
  public ResponseEntity<RestaurantInfoDTO> getRestaurantInfoByProductId(@PathVariable Long productId) {
    RestaurantInfoDTO restaurantInfo = productService.getRestaurantInfoByProductId(productId);
    if (restaurantInfo == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(restaurantInfo);
  }

  @GetMapping("/{productId}/restaurant")
  public String getRestaurantNameByProductId(@PathVariable Long productId) {
    return productService.getRestaurantNameByProductId(productId);
  }

  @GetMapping
  public ResponseEntity<List<ProductDTO>> listAllProducts() {
    List<ProductDTO> products = productService.getAllProducts();
    return ResponseEntity.ok(products);
  }

  @GetMapping("/restaurant")
  public ResponseEntity<List<ProductDTO>> listProductsByRestaurantId(@RequestHeader("Authorization") String token) {
    List<ProductDTO> products = productService.getProductsByRestaurantId(token);

    if (products.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(products);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
    ProductDTO productDTO = productService.getProductById(productId);
    return ResponseEntity.ok(productDTO);
  }

  @GetMapping("/search")
  public ResponseEntity<Page<ProductRestaurantDTO>> getProductById(@RequestParam(required = false) String produto, @RequestParam(required = false) String tipo, @RequestParam(required = false) String categoria, @RequestParam(required = false) String preco, @RequestParam(required = false) String currentpage) {
    Integer integerCurrentPage;
    if (currentpage == null) {
      integerCurrentPage = 0;
    } else {
      integerCurrentPage = Integer.parseInt(currentpage);
    }
    Page<ProductRestaurantDTO> productRestaurantDTO = productService.getFilteredProducts(produto, tipo, categoria, preco, integerCurrentPage);
    return ResponseEntity.ok(productRestaurantDTO);
  }

  @PostMapping
  public ResponseEntity<ProductDTO> createProduct(@RequestHeader("Authorization") String token,
                                                  @Valid @RequestBody ProductDTO productDTO) {

    ProductDTO createdProduct = productService.createProduct(productDTO, token);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{productId}")
        .buildAndExpand(createdProduct.productId())
        .toUri();
    return ResponseEntity.created(location).body(createdProduct);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ProductDTO> updateProduct(
      @PathVariable Long productId,
      @Valid @RequestBody ProductDTO productDTO) {

    ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
    return ResponseEntity.ok(updatedProduct);
  }

  @PatchMapping("/{productId}")
  public ResponseEntity<ProductDTO> partialUpdateProduct(
      @PathVariable Long productId,
      @Valid @RequestBody ProductPartialUpdateDTO productPartialUpdateDTO) {

    ProductDTO updatedProduct = productService.partialUpdateProduct(productId, productPartialUpdateDTO);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
    String originalFilename = file.getOriginalFilename();
    String normalizedFilename = originalFilename.replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_.]", ""); // Remove
    // caracteres
    // especiais

    // Define o caminho para o diretório de uploads
    String uploadDirPath = "refoods/src/main/resources/static/images/";
    File uploadDir = new File(uploadDirPath);

    // Cria o diretório se não existir
    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }
    try {
      Path destinationPath = uploadDir.toPath().resolve(normalizedFilename);
      Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
      return ResponseEntity.ok("http://localhost:8080/images/" + normalizedFilename);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer upload: " + e.getMessage());
    }
  }

  @GetMapping("/images/uploads/{fileName:.+}")
  public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
    // Define o caminho completo da imagem
    String imagePath = "refoods/src/main/resources/static/images/" + fileName;
    File imageFile = new File(imagePath);

    if (!imageFile.exists()) {
      return ResponseEntity.notFound().build(); // Retorna 404 se a imagem não existir
    }

    try {
      byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
      return ResponseEntity.ok()
          .contentType(MediaType.IMAGE_JPEG) // Altere para o tipo de imagem correto, se necessário
          .body(imageBytes);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(null); // Retorna 500 em caso de erro ao ler o arquivo
    }
  }
}
