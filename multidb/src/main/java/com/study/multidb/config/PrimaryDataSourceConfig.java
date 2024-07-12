package com.study.multidb.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.study.multidb.domains.student.StudentEntity;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = "com.study.multidb.domains.student",
	entityManagerFactoryRef = "studentEntityManagerFactory",
	transactionManagerRef = "studentTransactionManager"
)
public class PrimaryDataSourceConfig {

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.primary")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "studentEntityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean studentEntityManagerFactory(
		EntityManagerFactoryBuilder builder) {
		DataSource dataSource = primaryDataSource();
		return builder
			.dataSource(dataSource)
			.packages(StudentEntity.class)
			.build();
	}

	@Bean(name = "studentTransactionManager")
	@Primary
	public PlatformTransactionManager studentTransactionManager(
		@Qualifier("studentEntityManagerFactory") LocalContainerEntityManagerFactoryBean studentEntityManagerFactory) {
		return new JpaTransactionManager(studentEntityManagerFactory.getObject());
	}
}
