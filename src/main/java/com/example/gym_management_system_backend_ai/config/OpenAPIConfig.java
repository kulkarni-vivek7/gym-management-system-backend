package com.example.gym_management_system_backend_ai.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {

	@Bean
    OpenAPI customOpenAPI()
	{
    	
    	Info info = new Info();
    	info.title("Gym Management System APIs");
    	info.description("APIs For Managing All The Operations Of Admin, Trainers And Members");
    	info.version("1.0");
    	info.license(new License().name("Apache 2.0").url("http://springdoc.org"));
    	
    	SecurityScheme securityScheme = new SecurityScheme();
    	securityScheme.type(SecurityScheme.Type.HTTP);
    	securityScheme.scheme("bearer");
    	securityScheme.bearerFormat("JWT");
//    	
    	Components components = new Components();
    	components.addSecuritySchemes("bearerAuth", securityScheme);
//    	
    	SecurityRequirement securityRequirement = new SecurityRequirement();
    	securityRequirement.addList("bearerAuth");
    	
		OpenAPI openAPI = new OpenAPI();
		openAPI.info(info);
		openAPI.components(components);
		openAPI.security(List.of(securityRequirement));
		
		return openAPI;
	}
}
