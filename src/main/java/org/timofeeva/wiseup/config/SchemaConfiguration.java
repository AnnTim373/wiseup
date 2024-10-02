package org.timofeeva.wiseup.config;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Component
public class SchemaConfiguration implements BeanPostProcessor {

    @Value("${liquibase.default.db.schema}")
    private String schemaName;

    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, String beanName) throws BeansException {
        if (StringUtils.hasText(schemaName) && bean instanceof DataSource) {
            DataSource dataSource = (DataSource) bean;
            try (Connection conn = dataSource.getConnection();
                 Statement statement = conn.createStatement()) {
                log.info("Going to create DB schema '{}' if not exists.", schemaName);
                statement.execute("create schema if not exists " + schemaName);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to create schema '" + schemaName + "'", e);
            }
        }
        return bean;    }
}
