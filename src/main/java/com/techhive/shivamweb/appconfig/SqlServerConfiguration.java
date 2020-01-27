package com.techhive.shivamweb.appconfig;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
@EnableTransactionManagement
@EnableWebMvc
public class SqlServerConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSourceForDbConnection = new DriverManagerDataSource();
		dataSourceForDbConnection.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
		dataSourceForDbConnection.setUrl(env.getProperty("spring.datasource.url"));
		dataSourceForDbConnection.setUsername(env.getProperty("spring.datasource.username"));
		dataSourceForDbConnection.setPassword(env.getProperty("spring.datasource.password"));
		return dataSourceForDbConnection;

	}

	/**
	 * Declare the JPA entity manager factory.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryForLocal = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryForLocal.setDataSource(dataSource);

		// Classpath scanning of @Component, @Service, etc annotated class
		entityManagerFactoryForLocal.setPackagesToScan(env.getProperty("spring.datasource.packagesToScan"));

		// Vendor adapter
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryForLocal.setJpaVendorAdapter(vendorAdapter);

		// Hibernate properties

		Properties additionalProperties = new Properties();
		additionalProperties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
		additionalProperties.put("hibernate.id.new_generator_mappings", "false");
		additionalProperties.put("hibernate.show_sql", true);
		additionalProperties.put("hibernate.hbm2ddl.auto", "update");

		entityManagerFactoryForLocal.setJpaProperties(additionalProperties);

		return entityManagerFactoryForLocal;
	}

	/**
	 * Declare the transaction manager.
	 */
	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

//	@Override
//	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//		converters.add(mappingJackson2HttpMessageConverter());
//	}
//
//	@Bean
//	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
//		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//		converter.setObjectMapper(new HibernateAwareObjectMapper());
//		return converter;
//	}
//
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		converters.add(mappingJackson2HttpMessageConverter());
//	}

}
