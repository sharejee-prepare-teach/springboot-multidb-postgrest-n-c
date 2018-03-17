package com.foobar;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by : Ron Rith
 * Create Date: 03/17/2018.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "fooEntityManagerFactory",
        transactionManagerRef = "barTransactionManager", basePackages = {"com.foobar.foo.repo"})
public class FooDbConfigh {
    @Bean(name = "fooDataSource")
    //@Primary
    @ConfigurationProperties(prefix = "foo.datasource")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "fooEntityManagerFactory")
    //@Primary
    public LocalContainerEntityManagerFactoryBean fooEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("fooDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.foobar.foo.domain").persistenceUnit("foo")
                .build();
    }

    @Bean(name = "fooEntityManagerFactory")
    //@Primary
    public PlatformTransactionManager fooTransactionManager(
            @Qualifier("fooEntityManagerFactory") EntityManagerFactory barEntityManagerFactory) {
        return new JpaTransactionManager(barEntityManagerFactory);
    }
}
