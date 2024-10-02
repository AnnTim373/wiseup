package org.timofeeva.wiseup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.timofeeva.wiseup.config.properties.ApplicationProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class App {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        ConfigurableApplicationContext context = app.run(args);
        Environment env = context.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running!.\n\tAccess URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "Swagger: \thttp://localhost:{}/swagger-ui.html\n\t" +
                        "\n----------------------------------------------------------\n\t",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                env.getProperty("server.port"));
    }
}
