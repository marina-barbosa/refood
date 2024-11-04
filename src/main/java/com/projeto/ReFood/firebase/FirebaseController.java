package com.projeto.ReFood.firebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {

  @Autowired
  private FirebaseService firebaseService;

  // @PostMapping("/upload")
  // public ResponseEntity<String> uploadImage(@RequestParam("imageFile")
  // MultipartFile imageFile,
  // @RequestParam("imageName") String imageName) {
  // try {
  // firebaseService.upload(imageFile, imageName);
  // return ResponseEntity.ok("Upload successful!");
  // } catch (IOException e) {
  // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
  // .body("Failed to upload image: " + e.getMessage());
  // }
  // }

  @GetMapping("/image/{imageName}")
  public ResponseEntity<String> getImageUrl(@PathVariable String imageName) {
    String imageUrl = firebaseService.getImageUrl(imageName);
    if (imageUrl != null) {
      return ResponseEntity.ok(imageUrl);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
    try {
      String imageUrl = firebaseService.upload(file);
      return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>("Error uploading image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/image/{imageName}")
  public ResponseEntity<Void> deleteImage(@PathVariable String imageName) {
    boolean deleted = firebaseService.deleteImage(imageName);
    if (deleted) {
      return ResponseEntity.noContent().build(); // 204 No Content
    }
    return ResponseEntity.notFound().build(); // 404 Not Found
  }

  @DeleteMapping("/test")
  public ResponseEntity<String> testDelete() {
    return ResponseEntity.ok("DELETE method is supported");
  }

}
