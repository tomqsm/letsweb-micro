package biz.letsweb.micro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author tomasz
 */
@SpringBootApplication
public class LetswebMicroMain {

    public static final Logger LOG = LoggerFactory.getLogger(LetswebMicroMain.class);

    public static void main(String[] args) {
        LOG.info("Initialising ...");
        SpringApplication.run(LetswebMicroMain.class, args);
    }

}
