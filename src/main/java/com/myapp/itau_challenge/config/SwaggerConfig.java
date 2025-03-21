package com.myapp.itau_challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API de Transações - Itaú Backend Developer Júnior Challenge")
						.description("Desafio Backend Júnior - API para gerenciar transações e estatísticas.")
						.version("1.0")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation()
						.description("Mais informações")
						.url("https://github.com/ms-gustavo/itau-challenge"));
	}
	
}
