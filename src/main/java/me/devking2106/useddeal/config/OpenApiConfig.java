package me.devking2106.useddeal.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
	info = @Info(title = "used-deal API 명세서",
		description = "used-deal API 명세서",
		version = "v1",
		contact = @Contact(name = "devking2106", email = "devking2106@gmail.com"),
		license = @License(name = "Apache 2.0",
			url = "http://www.apache.org/licenses/LICENSE-2.0.html")
	)
)
@Configuration
public class OpenApiConfig {

}
