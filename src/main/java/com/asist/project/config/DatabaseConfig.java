package com.asist.project.config;

import org.hibernate.integrator.spi.Integrator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * createdAt: 11/13/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.asist.project.repository")
@PropertySource("classpath:data_source.properties")
public class DatabaseConfig {
    @Value("${data_source.driver_class_name}")
    private String driverClassName;
    @Value("${data_source.url}")
    private String url;
    @Value("${data_source.username}")
    private String username;
    @Value("${data_source.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean("entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, Integrator[] integrators) {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setHibernateIntegrators(integrators);
        bean.setPackagesToScan("com.asist.project.models");
        bean.setHibernateProperties(hibernateProperties());
        return bean;
    }

    @Bean
    public TransactionManager transactionManager(LocalSessionFactoryBean sessionFactoryBean) {
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(sessionFactoryBean.getObject());
        return manager;
    }

    @NotNull
    private static Properties hibernateProperties() {
        try {
            Properties properties = new Properties();
            properties.load(DatabaseConfig.class.getResourceAsStream("/hibernate.properties"));
            return properties;
        } catch (Exception e) {
            throw new RuntimeException("cannot read hibernate properties file");
        }
    }
}
