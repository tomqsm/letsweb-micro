package biz.letsweb.micro.camel;

import biz.letsweb.micro.LetswebMicroMain;
import java.util.concurrent.TimeUnit;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 
 * https://github.com/apache/camel/blob/master/examples/camel-example-spring-boot/src/test/java/sample/camel/SampleCamelApplicationTest.java
 */
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = LetswebMicroMain.class)
public class MyRouteTest {
    
    public MyRouteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   @Autowired
    private CamelContext camelContext;

    @Test
    public void shouldProduceMessages() throws Exception {
        // we expect that one or more messages is automatic done by the Camel
        // route as it uses a timer to trigger
        NotifyBuilder notify = new NotifyBuilder(camelContext).whenDone(1).create();
        assertTrue(notify.matches(10, TimeUnit.SECONDS));
    }
    
}
