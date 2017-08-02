package biz.letsweb.micro.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {
 
    @Override
    public void configure() throws Exception {
         from("timer:hello?period={{timer.period}}")
                .transform(method("myBean", "saySomething"))
                .to("stream:out");
    }
}