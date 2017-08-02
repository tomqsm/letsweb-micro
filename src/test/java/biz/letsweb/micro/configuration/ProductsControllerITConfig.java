package biz.letsweb.micro.configuration;

import biz.letsweb.micro.model.Product;
import biz.letsweb.micro.rest.service.ProductService;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.configuration.SwaggerCommonConfiguration;

/**
 * Exclude swagger loading as not needed to integration test of this controller.
 * see resource at https://david-codes.hatanian.com/2016/01/31/isolating-integration-tests-with-the-spring-framework.html
 *
 * @author tomasz
 */
@Configuration
@SpringBootTest(classes = ProductsControllerITConfig.class)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
//        org.apache.camel.component.direct.springboot.DirectComponentAutoConfiguration.class,
//        org.apache.camel.component.directvm.springboot.DirectVmComponentAutoConfiguration.class,
//        org.apache.camel.component.log.springboot.LogComponentAutoConfiguration.class,
//        org.apache.camel.component.properties.springboot.PropertiesComponentAutoConfiguration.class,
//        org.apache.camel.component.rest.springboot.RestComponentAutoConfiguration.class,
//        org.apache.camel.component.scheduler.springboot.SchedulerComponentAutoConfiguration.class,
//        org.apache.camel.component.seda.springboot.SedaComponentAutoConfiguration.class,
//        org.apache.camel.component.stub.springboot.StubComponentAutoConfiguration.class,
//        org.apache.camel.component.validator.springboot.ValidatorComponentAutoConfiguration.class,
//        org.apache.camel.component.vm.springboot.VmComponentAutoConfiguration.class,
//        org.apache.camel.component.xslt.springboot.XsltComponentAutoConfiguration.class,
//        org.apache.camel.impl.springboot.GzipDataFormatAutoConfiguration.class,
//        org.apache.camel.impl.springboot.SerializationDataFormatAutoConfiguration.class,
//        org.apache.camel.impl.springboot.StringDataFormatAutoConfiguration.class,
//        org.apache.camel.impl.springboot.ZipDataFormatAutoConfiguration.class,
//        org.apache.camel.language.bean.springboot.BeanLanguageAutoConfiguration.class,
//        org.apache.camel.language.constant.springboot.ConstantLanguageAutoConfiguration.class,
//        org.apache.camel.language.header.springboot.HeaderLanguageAutoConfiguration.class,
//        org.apache.camel.language.property.springboot.ExchangePropertyLanguageAutoConfiguration.class,
//        org.apache.camel.language.ref.springboot.RefLanguageAutoConfiguration.class,
//        org.apache.camel.language.simple.springboot.FileLanguageAutoConfiguration.class,
//        org.apache.camel.language.simple.springboot.SimpleLanguageAutoConfiguration.class,
//        org.apache.camel.language.tokenizer.springboot.TokenizeLanguageAutoConfiguration.class,
//        org.apache.camel.language.tokenizer.springboot.XMLTokenizeLanguageAutoConfiguration.class,
//        org.apache.camel.language.xpath.springboot.XPathLanguageAutoConfiguration.class,
//        org.apache.camel.spring.boot.CamelAutoConfiguration.class,
//        org.apache.camel.spring.boot.TypeConversionConfiguration.class
})
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
