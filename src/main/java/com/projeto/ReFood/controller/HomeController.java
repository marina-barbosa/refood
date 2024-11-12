package com.projeto.ReFood.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projeto.ReFood.exception.GlobalExceptionHandler.*;
import com.projeto.ReFood.model.UserInfo;
import com.projeto.ReFood.service.AuthService;

@Controller
public class HomeController {

  @Autowired
  private AuthService authService;

  @GetMapping({ "/", "" })
  public String redirectToSwagger() {
    return "redirect:/swagger-ui/index.html";
  }

  @PreAuthorize("hasAnyRole('ROLE_USER')")
  @GetMapping("/user")
  public ResponseEntity<String> isUser() {
    return ResponseEntity.ok("Você tem acesso de usuário.");
  }

  @PreAuthorize("hasAnyRole('ROLE_RESTAURANT')")
  @GetMapping("/restaurant")
  public ResponseEntity<String> isRestaurant() {
    return ResponseEntity.ok("Você tem acesso de restaurante.");
  }

  @GetMapping("/test-error")
  public ResponseEntity<Void> triggerNotFound() {
    // throw new NotFoundException();
    // throw new InternalServerErrorException();
    // throw new ForbiddenException();
    // throw new EmailAlreadyExistsException();
    // throw new CpfAlreadyExistsException();
    throw new CnpjAlreadyExistsException();
    // throw new BadCredentialsException();
    // throw new DatabaseException();
  }

  @GetMapping("/current-user")
  @ResponseBody
  public UserInfo rotaBifurcada() {
    UserInfo currentUser = authService.getCurrentUserInfo();
    return currentUser;
  }

  // @Value("${my.variable:Variable not found}")
  // private String myVariable;

  // @GetMapping("/show-variable")
  // @ResponseBody
  // public String showVariable() {
  //   // $Env:MY_VARIABLE = "hello from powershell"
  //   // Write-Output $Env:MY_VARIABLE
  //   System.out.println("Valor da variável: " + myVariable);
  //   return "Valor da variável de ambiente: " + myVariable;
  // }

  // @GetMapping("/getenv")
  // @ResponseBody
  // public Map<String, String> getEnv() {
  //   return System.getenv(); // Retorna todas as variáveis de ambiente
  // }

  // @GetMapping("/firebase-config")
  // @ResponseBody
  // public Map<String, String> getFirebaseConfig() {

  //   Map<String, String> envVariables = System.getenv();

  //   Map<String, String> firebaseConfig = new HashMap<>();
  //   firebaseConfig.put("FIREBASE_PROJECT_ID", envVariables.get("FIREBASE_PROJECT_ID"));
  //   firebaseConfig.put("FIREBASE_PRIVATE_KEY_ID", envVariables.get("FIREBASE_PRIVATE_KEY_ID"));
  //   firebaseConfig.put("FIREBASE_PRIVATE_KEY", envVariables.get("FIREBASE_PRIVATE_KEY"));
  //   firebaseConfig.put("FIREBASE_CLIENT_EMAIL", envVariables.get("FIREBASE_CLIENT_EMAIL"));
  //   firebaseConfig.put("FIREBASE_CLIENT_ID", envVariables.get("FIREBASE_CLIENT_ID"));
  //   firebaseConfig.put("FIREBASE_AUTH_URI", envVariables.get("FIREBASE_AUTH_URI"));
  //   firebaseConfig.put("FIREBASE_TOKEN_URI", envVariables.get("FIREBASE_TOKEN_URI"));
  //   firebaseConfig.put("FIREBASE_AUTH_PROVIDER_X509_CERT_URL",
  //       envVariables.get("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"));
  //   firebaseConfig.put("FIREBASE_CLIENT_X509_CERT_URL", envVariables.get("FIREBASE_CLIENT_X509_CERT_URL"));
  //   firebaseConfig.put("FIREBASE_UNIVERSE_DOMAIN", envVariables.get("FIREBASE_UNIVERSE_DOMAIN"));
  //   firebaseConfig.put("FIREBASE_STORAGE_BUCKET", envVariables.get("FIREBASE_STORAGE_BUCKET"));

  //   return firebaseConfig;
  // }

}
