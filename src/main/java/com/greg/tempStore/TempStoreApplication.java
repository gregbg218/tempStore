package com.greg.tempStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.Properties;

@SpringBootApplication
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

















