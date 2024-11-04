package com.projeto.ReFood.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class FirebaseService {

  @Value("${image.base.url}")
  private String imageBaseUrl;

  // public void upload(MultipartFile imageFile, String imageName) throws IOException {
  //   InputStream inputStream = imageFile.getInputStream();
  //   Bucket bucket = StorageClient.getInstance().bucket();
  //   bucket.create(imageName, inputStream, "image/jpeg");
  // }

  public String getImageUrl(String imageName) {
    Bucket bucket = StorageClient.getInstance().bucket();
    Blob blob = bucket.get(imageName);

    if (blob != null && blob.getMetadata() != null) {
      String token = blob.getMetadata().get("firebaseStorageDownloadTokens");
      if (token != null) {
        return String.format("%s/%s?alt=media&token=%s", imageBaseUrl, imageName, token);
      }
    }
    return null; // Retorna null se a imagem não tiver o token ou se não existir
  }

  // Método para fazer o upload da imagem e adicionar o token de download
  public String upload(MultipartFile file) throws IOException {
    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
    String downloadToken = UUID.randomUUID().toString(); // Gerando um token de download

    StorageClient storageClient = StorageClient.getInstance();
    Blob blob = storageClient.bucket().create(fileName, file.getInputStream(), file.getContentType());

    // Define o token de download como um metadado da imagem
    blob.toBuilder().setMetadata(Map.of("firebaseStorageDownloadTokens", downloadToken)).build().update();

    return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media&token=%s",
            storageClient.bucket().getName(), fileName, downloadToken);
  }

  public boolean deleteImage(String imageName) {
    Bucket bucket = StorageClient.getInstance().bucket();
    Blob blob = bucket.get(imageName);

    if (blob != null) {
      blob.delete(); // Deleta o blob
      return true;
    }
    return false; // Retorna false se a imagem não existir
  }
}
