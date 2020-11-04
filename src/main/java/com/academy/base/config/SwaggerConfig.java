package com.academy.base.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2) //
				.select() //
				.apis(RequestHandlerSelectors.any()) //
				.paths(getPaths()) //
				.build() //
				.apiInfo(apiInfo()) //
				.securitySchemes(securitySchemes());
	}

	private Predicate<String> getPaths() {
		final Predicate<String> excludePaths = Predicates.not(PathSelectors.regex("/error"));
		final Predicate<String> paths = PathSelectors.any();

		return Predicates.and(excludePaths, paths);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder() //
				.title("Academy Base Project") //
				.description("A testing project to demostrate spring boot cool features") //
				.version("1.0") //
				.build();
	}

	private static List<SecurityScheme> securitySchemes() {
		return Collections.singletonList(new ApiKey("Bearer", "Authorization", "header"));
	}
}
