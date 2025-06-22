package com.technical.evaluation.orders.shared.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Configuration
public class SwaggerConfig {

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.name}")
    private String appName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Environnement de développement"),
                        new Server().url("https://api.commandes.com").description("Production")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    private Info apiInfo() {
        return new Info()
                .title(appName + " - API REST")
                .description("API complète pour la gestion des commandes, clients, produits et analyses")
                .version(appVersion)
                .contact(new Contact()
                        .name("Équipe de Développement")
                        .email("dev@commandes.com")
                        .url("https://github.com/votre-repo/commandes-api")
                )
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT")
                );
    }
}
