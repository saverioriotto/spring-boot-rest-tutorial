package com.saverioriotto.springbootresttutorial.swagger;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

    public static final String DEFAULT_GROUP_NAME = "global";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String[] DEFAULT_INCLUDE_PATTERN = new String[] {
            "/api/test/admin",
            "/api/test/mod",
            "/api/test/utente",
            "/api/auth/registrazione",
            "/api/libro.*" };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.saverioriotto.springbootresttutorial"))
                .paths(PathSelectors.any())
                .build()
                .groupName(DEFAULT_GROUP_NAME)
                .securityContexts(securityContext())
                .securitySchemes(Lists.newArrayList(apiKey()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "API REST Springboot-Swagger2",
                "Esempio integrazione di Swagger v2 con API REST Springboot\n\n" +
                        "Credenziali di accesso demo: \n\n" +
                        "<b>Username: test</b>\n" +
                        "<b>Password: test<b>\n",
                "1.0",
                "Terms of service",
                new Contact("Saverio Riotto", "https://blog.saverioriotto.it", "info@saverioriotto.it"),
                "License of API", "https://blog.saverioriotto.it", Collections.emptyList());
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private List<SecurityContext> securityContext() {

        List<SecurityContext> securityContext = new ArrayList<>();

        for (String context : DEFAULT_INCLUDE_PATTERN){
            securityContext.add(SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .forPaths(PathSelectors.regex(context))
                    .build());
        }

        return securityContext;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }

}