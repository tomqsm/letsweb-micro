package biz.letsweb.micro.rest.service;

import biz.letsweb.micro.model.Product;
import org.springframework.stereotype.Service;

/**
 *
 * @author tomasz
 */
@Service
public class ProductService {
    public Product createExampleInstance(){
        return Product.builder()
                .withId("123qwe")
                .withName("example instance bicycle")
                .withPrice(123)
                .build();
    }
}
