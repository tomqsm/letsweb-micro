package biz.letsweb.micro.rest.model;

import biz.letsweb.micro.rest.controller.ProductsController;
import biz.letsweb.micro.rest.service.ProductService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 *
 * @author tomasz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration()
public class ProductControllerTest {

    @InjectMocks
    private ProductsController productController;

    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .setHandlerExceptionResolvers()
                .build();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Product.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetId() throws Exception {
        final ProductService productService = new ProductService();
        
        ReflectionTestUtils.setField(productController, "productService", productService);
        ReflectionTestUtils.setField(productController, "param1", "some test value");

        final MockHttpServletRequestBuilder get = get(ProductsController.PATH)
                .accept(MediaType.APPLICATION_JSON);
        
        final MvcResult restResult = this.mockMvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }
}
