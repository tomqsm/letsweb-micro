package biz.letsweb.micro.rest.controller;

import biz.letsweb.micro.configuration.ProductsControllerITConfig;
import biz.letsweb.micro.model.Product;
import java.net.URL;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author tomasz
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductsControllerITConfig.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIT {

    public static final Logger LOG = LoggerFactory.getLogger(ProductControllerIT.class);

    @LocalServerPort
    private int port;

    @Value("${server.contextPath}")
    private String serverContextPath;

    @Value("${info.app.name}")
    private String infoAppName;

    private URL base;
    
    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + serverContextPath + ProductsController.PATH);
    }

    @Test
    @Timed(millis = 12000)
    public void whenDataBaseIsUnavailableWith10secTimeout() throws Exception {
        final ResponseEntity<Product> response = template.getForEntity(base.toString(), Product.class);
        final Product body = response.getBody();
        LOG.info("{} received body: {}", infoAppName, body);
        assertThat(body).isNotNull();
    }

}
