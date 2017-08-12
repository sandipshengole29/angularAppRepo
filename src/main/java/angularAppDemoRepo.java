import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.angularAppDemoRepo")
public class angularAppDemoRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(angularAppDemoRepo.class);

    public static void main(final String[] args) {
        angularAppDemoRepo.LOGGER.info("-- Starting Application --");
        SpringApplication.run(angularAppDemoRepo.class, args);
    }

}
