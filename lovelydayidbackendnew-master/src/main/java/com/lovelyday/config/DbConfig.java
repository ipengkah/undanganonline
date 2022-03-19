package com.lovelyday.config;

/*import java.util.Properties;

import javax.persistence.EntityManagerFactory;*/
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
/*import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;*/

@Configuration
public class DbConfig {
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.1")
	public DataSourceProperties dataSourceProperties() {
	    return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.1")
	public DataSource dataSource(DataSourceProperties properties) {
	    return properties.initializeDataSourceBuilder()
	        .build();
	}

	//gua comment dulu bull 09042021
	/*`
	 * @Bean public EntityManagerFactory entityManagerFactory(DataSource dataSource)
	 * { HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	 * vendorAdapter.setDatabase(Database.POSTGRESQL);
	 * LocalContainerEntityManagerFactoryBean factory = new
	 * LocalContainerEntityManagerFactoryBean();
	 * 
	 * factory.setJpaVendorAdapter(vendorAdapter);
	 * factory.setPackagesToScan("com.lovelyday.model");
	 * factory.setDataSource(dataSource); factory.afterPropertiesSet();
	 * 
	 * Properties properties = new Properties();
	 * properties.setProperty("hibernate.dialect",
	 * "org.hibernate.dialect.H2Dialect");
	 * properties.setProperty("hibernate.hbm2ddl.auto", "update");
	 * factory.setJpaProperties(properties);
	 * 
	 * return factory.getObject(); }
	 */

}
