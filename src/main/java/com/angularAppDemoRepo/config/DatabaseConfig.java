package com.angularAppDemoRepo.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {
    "com.angularAppDemoRepo.repository"}, entityManagerFactoryRef = "localContainerEntityManagerFactoryBean")
@PropertySource(value = "classpath:/dataBase.properties")
@EnableTransactionManagement()

public class DatabaseConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${db.driver}")
    private String DATABASE_DRIVER;

    @Value("${db.password}")
    private String DATABASE_PASSWORD;

    @Value("${db.url}")
    private String DATABASE_URL;

    @Value("${db.userName}")
    private String DATABASE_USERNAME;

    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;

    @Value("${hibernate.format_sql}")
    private String HIBERNATE_FORMAT_SQL;

    @Value("${hibernate.implicit_naming_strategy}")
    private String HIBERNATE_IMPLICIT_NAMING_STRATEGY;

    @Value("${hibernate.packagesToScan}")
    private String HIBERNATE_PACKAGES_TO_SCAN;

    @Bean
    public DataSource dataSource(final Environment environment) {
        DatabaseConfig.LOGGER.info("** Inside DatabaseConfig @dataSource **");
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        try {
            driverManagerDataSource.setDriverClassName(this.DATABASE_DRIVER);
            driverManagerDataSource.setUrl(this.DATABASE_URL);
            driverManagerDataSource.setUsername(this.DATABASE_USERNAME);
            driverManagerDataSource.setPassword(this.DATABASE_PASSWORD);
        } catch (Exception ex) {
            DatabaseConfig.LOGGER.error("Exception occured in dataSource @DatabaseConfig: ", ex);
        }
        return driverManagerDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(final DataSource dataSource) {
        DatabaseConfig.LOGGER.info("** Inside DatabaseConfig @localContainerEntityManagerFactoryBean **");
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        try {
            /* setting data source */
            entityManagerFactoryBean.setDataSource(dataSource);

            /* setting Hibernate JPA vendor Adapter */
            entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

            /* packages containing @Entity annotation */
            entityManagerFactoryBean.setPackagesToScan(this.HIBERNATE_PACKAGES_TO_SCAN);

            /* properties related to Hibernate JPA vendor */
            Properties jpaProperties = new Properties();
            jpaProperties.put("hibernate.dialect", this.HIBERNATE_DIALECT);

            /*
             * comment this property if you are using the liquibase for
             * database chagnes
             */
            jpaProperties.put("hibernate.hbm2ddl.auto", this.HIBERNATE_HBM2DDL_AUTO);

            jpaProperties.put("hibernate.show_sql", this.HIBERNATE_SHOW_SQL);
            jpaProperties.put("hibernate.format_sql", this.HIBERNATE_FORMAT_SQL);
            jpaProperties.put("hibernate.implicit_naming_strategy", this.HIBERNATE_IMPLICIT_NAMING_STRATEGY);

            /*
             * setting all hibernate jpa properties to entityManageFactoryBean
             */
            entityManagerFactoryBean.setJpaProperties(jpaProperties);
            DatabaseConfig.LOGGER.info("** In entityManagerFactoryBean **" + entityManagerFactoryBean);

        } catch (Exception e) {
            DatabaseConfig.LOGGER.error("Exception occured in localContainerEntityManagerFactoryBean @DatabaseConfig: ", e);
        }
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactoryBean) {
        DatabaseConfig.LOGGER.info("* In Transaction Manager *");
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        try {
            transactionManager.setEntityManagerFactory(entityManagerFactoryBean);
        } catch (Exception e) {
            DatabaseConfig.LOGGER.error("Exception occured in transactionManager @DatabaseConfig: ", e);
        }
        DatabaseConfig.LOGGER.info("* After Transaction Manager *");
        return transactionManager;
    }
}
