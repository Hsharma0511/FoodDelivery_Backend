package com.fooddelivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Repository.AuthRepository;
import com.fooddelivery.entity.Customers;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthRepository authRepository;
	
	@Override
	public Customers login(String customer_email, String customer_password) {
		return authRepository.getCustomerByEmailAndPassword(customer_email, customer_password);
	}

}
