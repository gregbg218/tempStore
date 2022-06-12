package com.greg.tempStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


import java.util.Properties;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class})
public class TempStoreApplication {
//	public static Properties projectProperties = new Properties();

	public static void main(String[] args) {
//		configureProperties(args);
		SpringApplication.run(TempStoreApplication.class, args);
	}

//	public static void configureProperties(String[] args) {
//		projectProperties = new Properties();
//		for (int i = 0; i < args.length; i++) {
//			if (args[i].contains("--")) {
//				String key = args[i].replaceAll("--", "");
//				projectProperties.put(key, args[i + 1]);
//			}
//		}
//	}

}

















