package search.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

//@Profile({"local", "dev"})
//@EnableWebMvc
@Configuration
public class SwaggerConfig {
	
//	@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        registry.addResourceHandler("/swagger-resources/**").addResourceLocations("classpath:/META-INF/resources/");
//        super.addResourceHandlers(registry);
//    }

	@Bean
	public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
		Info info = new Info()
				.title("SEARCH API")
				.version(springdocVersion)
				.description("SEARCH API " + springdocVersion);
		
		return new OpenAPI()
				.components(new Components())
				.info(info);
	}
}
