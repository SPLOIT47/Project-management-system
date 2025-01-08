package com.crm.infrastructure.dataaccess.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.crm.infrastructure.dataaccess.repository")
public class JpaConfig {
    private final Dotenv dotenv = Dotenv.configure().directory(System.getProperty("user.dir")).load();

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dotenv.get("DATABASE_URL"));
        dataSource.setUsername(dotenv.get("DATABASE_USER"));
        dataSource.setPassword(dotenv.get("DATABASE_PASSWORD"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.crm.domain.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setEntityManagerFactoryInterface(EntityManagerFactory.class);
        em.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "update");
        em.getJpaPropertyMap().put("hibernate.show_sql", "true");
        em.getJpaPropertyMap().put("hibernate.globally_quoted_identifiers", "true");
        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }
}
