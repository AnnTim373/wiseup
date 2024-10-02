package org.timofeeva.wiseup.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class LiquibaseConfiguration {

    @Value("${liquibase.default.db.schema}")
    private String schema;

    @Bean
    @ConditionalOnProperty(name = "spring.liquibase.enabled", havingValue = "true")
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(schema);
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        log.debug("Configuring Liquibase");
        return liquibase;
    }
}
