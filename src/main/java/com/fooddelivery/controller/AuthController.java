package com.fooddelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.entity.Customers;
import com.fooddelivery.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200/")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@GetMapping("/login/{customer_email}/{customer_password}")
	private ResponseEntity<Customers> loginCustomer(@PathVariable("customer_email") String customer_email, @PathVariable("customer_password") String customer_password) {
		return new ResponseEntity<Customers>(authService.login(customer_email, customer_password), HttpStatus.OK);
	}
}
