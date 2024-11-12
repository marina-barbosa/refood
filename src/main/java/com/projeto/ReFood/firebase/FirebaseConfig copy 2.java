// package com.projeto.ReFood.firebase;

// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.beans.factory.annotation.Value;

// import java.io.ByteArrayInputStream;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;

// @Configuration
// public class FirebaseConfig {

// @Value("${firebase.project-id}")
// private String projectId;

// @Value("${firebase.private-key-id}")
// private String privateKeyId;

// @Value("${firebase.private-key}")
// private String privateKey;

// @Value("${firebase.client-email}")
// private String clientEmail;

// @Value("${firebase.client-id}")
// private String clientId;

// @Value("${firebase.auth-uri}")
// private String authUri;

// @Value("${firebase.token-uri}")
// private String tokenUri;

// @Value("${firebase.auth-provider-x509-cert-url}")
// private String authProviderCertUrl;

// @Value("${firebase.client-x509-cert-url}")
// private String clientCertUrl;

// @Value("${firebase.universe-domain}")
// private String universeDomain;

// @Value("${firebase.storage-bucket}")
// private String storageBucket;

// @Bean
// public FirebaseApp initializeFirebase() throws IOException {
// System.out.println("Project ID: " + projectId);
// System.out.println("Private Key ID: " + privateKeyId);
// System.out.println("Client Email: " + clientEmail);

// // Verifique se as variáveis estão corretas
// if (projectId == null || privateKey == null || clientEmail == null) {
// throw new IllegalArgumentException("Some Firebase environment variables are
// not set.");
// }

// // Criando um mapa com as configurações do Firebase
// Map<String, Object> firebaseConfig = new HashMap<>();
// firebaseConfig.put("type", "service_account");
// firebaseConfig.put("project_id", projectId);
// firebaseConfig.put("private_key_id", privateKeyId);
// firebaseConfig.put("private_key", privateKey.replace("\\n", "\n"));
// firebaseConfig.put("client_email", clientEmail);
// firebaseConfig.put("client_id", clientId);
// firebaseConfig.put("auth_uri", authUri);
// firebaseConfig.put("token_uri", tokenUri);
// firebaseConfig.put("auth_provider_x509_cert_url", authProviderCertUrl);
// firebaseConfig.put("client_x509_cert_url", clientCertUrl);

// // Convertendo o mapa para um InputStream
// ByteArrayInputStream serviceAccountStream = new ByteArrayInputStream(
// new
// com.fasterxml.jackson.databind.ObjectMapper().writeValueAsBytes(firebaseConfig));

// FirebaseOptions options = FirebaseOptions.builder()
// .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
// .setProjectId(projectId)
// .setStorageBucket(storageBucket)
// .build();

// return FirebaseApp.initializeApp(options);
// }
// }
