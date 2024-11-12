package com.projeto.ReFood.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Configuration
public class FirebaseConfig {

  @Bean
  public FirebaseApp initializeFirebase() throws Exception {
    // Obter variáveis de ambiente
    Map<String, String> envVariables = System.getenv();

    // Obter todas as partes da chave privada
    String privateKeyPart1 = envVariables.get("FIREBASE_PRIVATE_KEY_PART1");
    String privateKeyPart2 = envVariables.get("FIREBASE_PRIVATE_KEY_PART2");
    String privateKeyPart3 = envVariables.get("FIREBASE_PRIVATE_KEY_PART3");
    String privateKeyPart4 = envVariables.get("FIREBASE_PRIVATE_KEY_PART4");
    String privateKeyPart5 = envVariables.get("FIREBASE_PRIVATE_KEY_PART5");
    String privateKeyPart6 = envVariables.get("FIREBASE_PRIVATE_KEY_PART6");

    // Concatenar as partes da chave privada
    String privateKey = privateKeyPart1 + privateKeyPart2 + privateKeyPart3 +
        privateKeyPart4 + privateKeyPart5 + privateKeyPart6;

    // Substituir \n por quebras de linha
    privateKey = privateKey.replace("\\n", "\n");

    // Criar a string de credenciais JSON com as variáveis de ambiente
    String credentialsJson = String.format(
        "{ \"type\": \"service_account\", \"project_id\": \"%s\", \"private_key_id\": \"%s\", \"private_key\": \"%s\", \"client_email\": \"%s\", \"client_id\": \"%s\", \"auth_uri\": \"%s\", \"token_uri\": \"%s\", \"auth_provider_x509_cert_url\": \"%s\", \"client_x509_cert_url\": \"%s\", \"universe_domain\": \"%s\" }",
        envVariables.get("FIREBASE_PROJECT_ID"),
        envVariables.get("FIREBASE_PRIVATE_KEY_ID"),
        privateKey,
        envVariables.get("FIREBASE_CLIENT_EMAIL"),
        envVariables.get("FIREBASE_CLIENT_ID"),
        envVariables.get("FIREBASE_AUTH_URI"),
        envVariables.get("FIREBASE_TOKEN_URI"),
        envVariables.get("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"),
        envVariables.get("FIREBASE_CLIENT_X509_CERT_URL"),
        envVariables.get("FIREBASE_UNIVERSE_DOMAIN"));

    // Converter a string JSON para um InputStream
    ByteArrayInputStream credentialsStream = new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8));

    // Criar as credenciais a partir do InputStream
    GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

    // Configurar as opções do Firebase
    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(credentials)
        .setStorageBucket(envVariables.get("FIREBASE_STORAGE_BUCKET"))
        .build();

    // Inicializar e retornar o FirebaseApp
    return FirebaseApp.initializeApp(options);
  }

}
