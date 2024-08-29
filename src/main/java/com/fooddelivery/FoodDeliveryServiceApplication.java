package com.fooddelivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fooddelivery.model.Customers;
import com.fooddelivery.service.CustomersService;

@SpringBootApplication
public class FoodDeliveryServiceApplication implements CommandLineRunner {
	
	@Autowired
	CustomersService cs;

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		Customers c = new Customers(500, "Sumit", "sumit@gmail.com", "3458794574");
//		
//		cs.addCustomer(c);
	}

}
