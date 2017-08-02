package biz.letsweb.micro.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

/**
 *
 * @author tomasz
 */
@Configuration
public class TuckeyUrlrewriteFilterConfig {

    @Bean
    public FilterRegistrationBean urlRewriteFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registerUrlRewriteFilter(registrationBean);
        return registrationBean;
    }

    private void registerUrlRewriteFilter(FilterRegistrationBean registrationBean) {
        UrlRewriteFilter urlRewriteFilter = new UrlRewriteFilter();
        registrationBean.setName("TuckeyUrlrewriteFilter");
        List<String> urlPatterns = new ArrayList<>(2);
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setDispatcherTypes(
                DispatcherType.REQUEST
        );
        registrationBean.setAsyncSupported(true);

        Map<String, String> initParams = new HashMap<>(5);
        initParams.put("confReloadCheckInterval", "5");
        initParams.put("logLevel", "debug");
        initParams.put("statusPath", "/status");
        initParams.put("confPath", "urlrewrite.xml");

        registrationBean.setInitParameters(initParams);
        registrationBean.setFilter(urlRewriteFilter);
        registrationBean.setOrder(1);
    }

}
