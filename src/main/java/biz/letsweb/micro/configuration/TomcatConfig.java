package biz.letsweb.micro.configuration;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SocketUtils;
import ch.qos.logback.access.tomcat.LogbackValve;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomasz
 */
@Configuration
public class TomcatConfig {

    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    @Bean
    public int port() {
        return SocketUtils.findAvailableTcpPort();
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();

        LogbackValve logbackValve = new LogbackValve();
        logbackValve.setFilename("src/main/resources/logback-access.xml");

        tomcatFactory.setTomcatContextCustomizers(Collections.singletonList(context -> {
            context.setSessionTimeout(30);
        }));
        tomcatFactory.setTomcatConnectorCustomizers(
                Collections.singletonList(context -> context.setProperty("Server", "zorro"))
        );

        return tomcatFactory;
    }


}
