package com.projeto.ReFood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@EnableSpringDataWebSupport //Para fazer o carregamento de páginas infinitas
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class
ReFoodsApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReFoodsApplication.class, args);
	}
}
