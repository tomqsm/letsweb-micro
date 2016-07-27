package biz.letsweb.micro.configuration;

import biz.letsweb.micro.rest.controller.ProductsController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import static javafx.scene.input.KeyCode.T;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(ProductsController.class.getPackage().getName())
                )
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/api/v1")
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "letsweb-micro",
                "Exemple of basic Springboot configuration with basic API.",
                "/api/v1",
                "Terms of service",
                "myeaddress@letsweb.biz",
                "License of API",
                "http://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }
}
