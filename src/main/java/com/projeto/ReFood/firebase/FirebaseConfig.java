package com.projeto.ReFood.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

  @Bean
  public FirebaseApp initializeFirebase() throws IOException {
    // Obtendo as variáveis de ambiente
    String projectId = System.getenv("FIREBASE_PROJECT_ID");
    String privateKey = System.getenv("FIREBASE_PRIVATE_KEY");
    String clientEmail = System.getenv("FIREBASE_CLIENT_EMAIL");

    // Construir o JSON com as credenciais a partir das variáveis de ambiente
    String jsonCredentials = String.format(
        "{ \"type\": \"service_account\", " +
            "\"project_id\": \"%s\", " +
            "\"private_key\": \"%s\", " +
            "\"client_email\": \"%s\", " +
            "\"auth_uri\": \"%s\", " +
            "\"token_uri\": \"%s\", " +
            "\"auth_provider_x509_cert_url\": \"%s\", " +
            "\"client_x509_cert_url\": \"%s\" }",
        projectId,
        privateKey,
        clientEmail,
        System.getenv("FIREBASE_AUTH_URI"),
        System.getenv("FIREBASE_TOKEN_URI"),
        System.getenv("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"),
        System.getenv("FIREBASE_CLIENT_X509_CERT_URL"));

    // Criando o InputStream para o GoogleCredentials a partir do JSON gerado
    InputStream credentialsStream = new java.io.ByteArrayInputStream(jsonCredentials.getBytes());

    // Configurando o Firebase com as credenciais fornecidas
    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(credentialsStream))
        .setStorageBucket("refood-storage.appspot.com") // Modifique com seu bucket, se necessário
        .build();

    // Inicializando o Firebase
    return FirebaseApp.initializeApp(options);
  }
}
