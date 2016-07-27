package biz.letsweb.micro.configuration;

import biz.letsweb.micro.model.Product;
import biz.letsweb.micro.rest.service.ProductService;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Exclude swagger loading as not needed to integration test of this controller.
 * see resource at https://david-codes.hatanian.com/2016/01/31/isolating-integration-tests-with-the-spring-framework.html
 * @author tomasz
 */
@Configuration
@SpringApplicationConfiguration(classes = ProductsControllerITConfig.class)
@EnableAutoConfiguration(exclude = {SwaggerConfig.class})
@ComponentScan(basePackages = "biz.letsweb.micro.rest")
public class ProductsControllerITConfig {

    @Bean
    public ProductService productService() {
        final ProductService mock = Mockito.mock(ProductService.class);
        when(mock.createExampleInstance()).thenReturn(Product.builder()
                .withId("testid")
                .withName("testname")
                .withPrice(123).build());
        return mock;
    }
}
