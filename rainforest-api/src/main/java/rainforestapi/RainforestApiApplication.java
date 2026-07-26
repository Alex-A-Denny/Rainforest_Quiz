package rainforestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main Spring Boot application class for the Rainforest Quiz API.
 * 
 * This application serves as the backend for the Rainforest Quiz application,
 * providing REST endpoints for user management and badge tracking. It configures
 * CORS settings to allow communication with the Angular frontend running on
 * localhost:4200.
 * 
 * The application uses Spring Data JPA for database access and MySQL as the
 * persistence layer.
 * 
 * @author Alex Denny
 * @version 1.0
 */
@SpringBootApplication
public class RainforestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RainforestApiApplication.class, args);
    }

    /**
     * Configures Cross-Origin Resource Sharing (CORS) for the application.
     * 
     * Allows the Angular frontend running on http://localhost:4200 to make requests
     * to this API with credentials enabled. Supports all standard HTTP methods and headers.
     * 
     * @return A WebMvcConfigurer bean configured with CORS settings
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:4200")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}
