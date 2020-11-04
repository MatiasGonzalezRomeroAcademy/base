package com.academy.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

import com.academy.base.properties.CalculatorProperties;
import com.academy.base.properties.JwtSecurityProperties;
import com.academy.base.properties.UploadFilesProperties;

@SpringBootApplication
@EnableConfigurationProperties({ //
		CalculatorProperties.class, //
		JwtSecurityProperties.class, //
		UploadFilesProperties.class //
})
@EnableCaching
public class BaseApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}

	@Override
	public SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(BaseApplication.class);
	}

}
