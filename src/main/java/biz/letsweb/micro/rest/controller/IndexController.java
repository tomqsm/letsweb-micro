package biz.letsweb.micro.rest.controller;

import static biz.letsweb.micro.rest.controller.IndexController.PATH;
import biz.letsweb.micro.rest.model.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
public class IndexController {
    
        public static final String PATH = "/products";
    @RequestMapping(
            method = RequestMethod.GET)
    @ApiOperation(
            response = Product.class,
            notes = "Return a list of all (with an option of specifying products ids in amount fitting in GET url) products from database.",
            value = "Return a list of all (with an option of specifying products ids in amount fitting in GET url) products from database.")
    public Product getProduct() {
        return Product.builder()
                .withId("123qwe")
                .withName("bicycle")
                .withPrice(123)
                .build();
    }
}
