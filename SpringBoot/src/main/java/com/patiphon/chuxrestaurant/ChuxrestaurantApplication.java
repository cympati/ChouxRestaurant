package com.patiphon.chuxrestaurant;

import com.patiphon.chuxrestaurant.database.MySQLConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChuxrestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChuxrestaurantApplication.class, args);
		try {
			new MySQLConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
