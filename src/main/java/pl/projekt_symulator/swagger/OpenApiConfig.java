package pl.projekt_symulator.swagger;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class OpenApiConfig {
   // http://localhost:8080/swagger-ui/index.html#/


     @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .components((new Components()))
                .info(new Info().title( "Symulator").description("Symulator strzelecki")
                .version( "1.0"));

    }


}