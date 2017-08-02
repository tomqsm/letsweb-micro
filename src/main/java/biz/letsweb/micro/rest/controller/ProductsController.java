package biz.letsweb.micro.rest.controller;

import static biz.letsweb.micro.rest.controller.ProductsController.PATH;
import biz.letsweb.micro.model.Product;
import biz.letsweb.micro.rest.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tomasz
 */
@Api(description = "products endpoint", tags = {"products"}, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsController {

    public static final String PATH = "/products";
    public static final Logger LOG = LoggerFactory.getLogger(ProductsController.class);

    @Value("${micro.controller.products.param1:defaultValue}")
    private String param1;
    @Value("${info.app.commit:00000}")
    private String commit;

    @Autowired
    private ProductService productService;

    @GetMapping
    @ApiOperation(
            response = Product.class,
            notes = "Return a list of all products from database.",
            value = "Return a list of all products from database.")
    public Product getProduct() {
        LOG.info("properties param1: {}, commit {}",param1, commit);
        final Product product = productService.createExampleInstance();
        return product;
    }
}
