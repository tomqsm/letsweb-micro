package biz.letsweb.micro.rest.controller;

import static biz.letsweb.micro.rest.controller.ProductController.PATH;
import biz.letsweb.micro.model.Product;
import biz.letsweb.micro.rest.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tomasz
 */
@Api(description = "products endpoint", tags = {"products"}, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    public static final String PATH = "/products";
    
    @Autowired
    private ProductService productService;

    @RequestMapping(
            method = RequestMethod.GET)
    @ApiOperation(
            response = Product.class,
            notes = "Return a list of all products from database.",
            value = "Return a list of all products from database.")
    public Product getProduct() {
        final Product product = productService.createExampleInstance();
        return product;
    }
}
