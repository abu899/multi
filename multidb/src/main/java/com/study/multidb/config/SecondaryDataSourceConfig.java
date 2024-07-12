package com.study.multidb.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.study.multidb.domains.school.SchoolEntity;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = "com.study.multidb.domains.school",
	entityManagerFactoryRef = "schoolEntityManagerFactory",
	transactionManagerRef = "schoolTransactionManager"
)
public class SecondaryDataSourceConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.secondary")
	public DataSource secondaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "schoolEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
		EntityManagerFactoryBuilder builder) {
		return builder
			.dataSource(secondaryDataSource())
			.packages(SchoolEntity.class)
			.build();
	}

	@Bean(name = "schoolTransactionManager")
	public PlatformTransactionManager secondaryTransactionManager(
		@Qualifier("schoolEntityManagerFactory") LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory) {
		return new JpaTransactionManager(secondaryEntityManagerFactory.getObject());
	}
}
