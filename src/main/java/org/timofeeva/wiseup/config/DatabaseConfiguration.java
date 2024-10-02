package org.timofeeva.wiseup.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "org.timofeeva.wiseup.repository",
        entityManagerFactoryRef = "wiseupEntityManager",
        transactionManagerRef = "wiseupTransactionManager")
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties wiseupDataSourceDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.configuration")
    public HikariDataSource wiseupDataSource() {
        return wiseupDataSourceDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "wiseupEntityManager")
    public LocalContainerEntityManagerFactoryBean wiseEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(wiseupDataSource());
        em.setPackagesToScan("org.timofeeva.wiseup.domain");
        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.default_schema", "org/timofeeva/wise");
        em.setJpaProperties(jpaProperties);
        return em;
    }

    @Primary
    @Bean(name = "wiseupTransactionManager")
    public PlatformTransactionManager wiseTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(wiseEntityManager().getObject());
        return transactionManager;
    }
}
