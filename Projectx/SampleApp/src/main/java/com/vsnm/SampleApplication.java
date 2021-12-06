package com.vsnm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.vsnm.framework.repository.BaseJpaRepositoryImpl;
import com.vsnm.framework.repository.BaseMongoRepositoryImpl;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories(repositoryBaseClass = BaseJpaRepositoryImpl.class)
@EnableMongoRepositories(repositoryBaseClass = BaseMongoRepositoryImpl.class)
public class SampleApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(SampleApplication.class);
	}
}