// package com.projeto.ReFood.firebase;

// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.io.FileInputStream;
// import java.io.IOException;

// @Configuration
// public class FirebaseConfig {

//   String pathKey = "/refoods/src/main/java/com/projeto/ReFood/firebase/refood-firebase-key.jsonc";

//   @Bean
//   public FirebaseApp initializeFirebase() throws IOException {
//     String serviceAccountPath = System.getProperty("user.dir") + pathKey;
//     FileInputStream serviceAccountStream = new FileInputStream(serviceAccountPath);

//     FirebaseOptions options = FirebaseOptions.builder()
//         .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
//         .setStorageBucket("refood-storage.appspot.com")
//         .build();
//     return FirebaseApp.initializeApp(options);
//   }

// }
