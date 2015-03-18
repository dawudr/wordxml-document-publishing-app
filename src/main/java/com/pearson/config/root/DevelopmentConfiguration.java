package com.pearson.config.root;


import com.pearson.app.init.TestDataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Development specific configuration - creates a localhost postgresql datasource,
 * sets hibernate on create drop mode and inserts some test data on the database.
 *
 * Set -Dspring.profiles.active=development to activate this config.
 *
 */
@Configuration
@Profile("development")
@EnableTransactionManagement
public class DevelopmentConfiguration {

    @Bean(initMethod = "init")
    public TestDataInitializer initTestData() {
        return new TestDataInitializer();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopmentConfiguration.class);

    @Bean(name = "datasource")
    public DriverManagerDataSource dataSource() {

        // Read RDS Connection Information from the Environment
        String jdbc_connection_string = System.getProperty("JDBC_CONNECTION_STRING");
        String dbName = System.getProperty("RDS_DB_NAME");
        String userName = System.getProperty("RDS_USERNAME");
        String password = System.getProperty("RDS_PASSWORD");
        String hostname = System.getProperty("RDS_HOSTNAME");
        String port = System.getProperty("RDS_PORT");

        LOGGER.debug("JVM System Properties: JDBC_CONNECTION_STRING[{}]", jdbc_connection_string);
        LOGGER.debug("JVM System Properties: RDS_DB_NAME[{}], RDS_HOSTNAME[{}], RDS_PORT[{}], RDS_USERNAME[{}], RDS_PASSWORD[{}]", dbName, hostname, port, userName, password);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(com.mysql.jdbc.Driver.class.getName());

        if(dbName != null && userName != null && password != null && hostname != null &&
                !dbName.isEmpty() && !userName.isEmpty() && !password.isEmpty() && !hostname.isEmpty()) {
            LOGGER.debug("Using Amazon RDS Custom database properties: {}: RDS_DB_NAME[{}], RDS_HOSTNAME[{}], RDS_PORT[{}], RDS_USERNAME[{}], RDS_PASSWORD[{}]", dbName, hostname, port, userName, password);
            // Amazon RDS pearsonbtec.c9o15qzo1tvt.us-east-1.rds.amazonaws.com
            // jdbc:mysql://pearsonbtec.c9o15qzo1tvt.us-east-1.rds.amazonaws.com:3306/pearsonbtecquals
            dataSource.setUrl("jdbc:mysql://" + hostname + ":" + port + "/" + dbName);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);
        } else if(jdbc_connection_string != null && !jdbc_connection_string.isEmpty()) {
            LOGGER.debug("Using Amazon RDS JDBC_CONNECTION_STRING: {}", jdbc_connection_string);
            dataSource.setUrl(jdbc_connection_string);
        } else {
            //defaults
            LOGGER.debug("Using default MySQL database: jdbc:mysql://localhost:3306/pearsonbtecquals");
            dataSource.setUrl("jdbc:mysql://localhost:3306/pearsonbtecquals");
            dataSource.setUsername("root");
            dataSource.setPassword("password");
        }

        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan(new String[]{"com.pearson.app.model"});
        entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> jpaProperties = new HashMap<String, Object>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "create");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.use_sql_comments", "true");
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);

        return entityManagerFactoryBean;
    }

}
