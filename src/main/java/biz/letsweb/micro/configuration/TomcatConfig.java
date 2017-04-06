package biz.letsweb.micro.configuration;

import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SocketUtils;
import java.util.Collections;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.jndi.JndiObjectFactoryBean;

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
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        final TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory = new TomcatEmbeddedServletContainerFactory() {

            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
                    Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/myDataSource");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "your.db.Dri");
                resource.setProperty("url", "jdbc:h2:mem:testdb;Mode=Oracle");

                context.getNamingResources().addResource(resource);
            }
        };
        tomcatEmbeddedServletContainerFactory.setTomcatContextCustomizers(Collections.singletonList(context -> {
            context.setSessionTimeout(30);
        }));
        tomcatEmbeddedServletContainerFactory.setTomcatConnectorCustomizers(
                Collections.singletonList(context -> context.setProperty("Server", "zorro"))
        );
        return tomcatEmbeddedServletContainerFactory;
    }

    @Bean(destroyMethod = "")
    public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/myDataSource");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }
}
