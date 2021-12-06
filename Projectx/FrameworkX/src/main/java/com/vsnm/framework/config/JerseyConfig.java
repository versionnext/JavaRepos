package com.vsnm.framework.config;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.SwaggerConfigLocator;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.jaxrs.listing.ApiListingResource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

	
	public JerseyConfig(@Value("${swagger.path}") String swaggerPath) {
		BeanConfig swaggerConfig = new BeanConfig();
		swaggerConfig.setBasePath(swaggerPath);
		SwaggerConfigLocator.getInstance().putConfig(
				SwaggerContextService.CONFIG_ID_DEFAULT, swaggerConfig);
		packages("com.vsnm", ApiListingResource.class.getPackage().getName());
	}

	@Component
	@Primary
	public static class CombinedSwaggerResourcesProvider implements
			SwaggerResourcesProvider {

		@Resource
		private InMemorySwaggerResourcesProvider inMemorySwaggerResourcesProvider;

		@Override
		public List<SwaggerResource> get() {

			SwaggerResource jerseySwaggerResource = new SwaggerResource();
			jerseySwaggerResource.setLocation("/api/swagger.json");
			jerseySwaggerResource.setSwaggerVersion("2.0");
			jerseySwaggerResource.setName("Jersey");

			return Stream.concat(Stream.of(jerseySwaggerResource),
					inMemorySwaggerResourcesProvider.get().stream()).collect(
					Collectors.toList());
		}

	}
}
