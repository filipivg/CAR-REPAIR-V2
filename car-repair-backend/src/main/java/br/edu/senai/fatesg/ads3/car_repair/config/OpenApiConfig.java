package br.edu.senai.fatesg.ads3.car_repair.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Car Repair API")
                        .description("API do sistema de gestão de oficina mecânica")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SENAI FATESG ADS3")
                                .email("ads3@fatesg.edu.br")));
    }
}
