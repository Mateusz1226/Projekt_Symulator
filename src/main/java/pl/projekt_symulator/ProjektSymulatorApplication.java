package pl.projekt_symulator;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@OpenAPIDefinition(info = @Info(title = "Symulator",version = "1.0", description = "Symulator strzelecki"))
public class ProjektSymulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektSymulatorApplication.class, args);
    }

}
