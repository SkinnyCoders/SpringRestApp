package com.tanto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SweggerConfig {
	@Bean
	public Docket myApi() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo());

	}

//	@SuppressWarnings("deprecation")/
	private ApiInfo getApiInfo() {

		// We do not use all info
		@SuppressWarnings("deprecation")
		ApiInfo info = new ApiInfo("Rest Data", "Auto generated Rest API Documentation", "1.0",
				"", "", "", "");

		return info;
	}

}