package com.fooddelivery.controller;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.Exception.CustomerNotFoundException;
import com.fooddelivery.Exception.DuplicateCustomerIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.entity.Customers;
import com.fooddelivery.entity.Orders;
import com.fooddelivery.entity.Ratings;
import com.fooddelivery.entity.Restaurants;
import com.fooddelivery.service.CustomersService;
import com.fooddelivery.service.RestaurantsService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:4200/")
public class CustomersController {
	@Autowired
	private CustomersService customersService;
	
	@Autowired
	private RestaurantsService restaurantsService;

	@GetMapping("/")
	public ResponseEntity<List<Customers>> getAllCustomers() {
		List<Customers> customers = customersService.getAllCustomers();
		return new ResponseEntity<List<Customers>>(customers, HttpStatus.OK);
	}

	@GetMapping("/{customer_id}")
	public ResponseEntity<Customers> getCustomerById(@PathVariable("customer_id") int customer_id) {
		Customers customer = customersService.getCustomerById(customer_id);
		
		if(customer == null) {
			throw new CustomerNotFoundException(" with ID: " + customer_id);
		}
		return new ResponseEntity<Customers>(customer, HttpStatus.OK);
	}
	
	

	@PostMapping("/")
	public ResponseEntity<Customers> addCustomer(@RequestBody @Valid Customers customer) {
		try {
			if (customersService.getCustomerById(customer.getCustomer_id()) != null) {
				throw new DuplicateCustomerIDException("Customer with ID " + customer.getCustomer_id() + " already exists");
			}
			return new ResponseEntity<Customers>(customersService.addCustomer(customer), HttpStatus.OK);
		} catch (DuplicateCustomerIDException e) {
			return new ResponseEntity<Customers>(HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/{customer_id}")
	public ResponseEntity<Customers> updateCustomerById(@PathVariable("customer_id") int customer_id, @RequestBody @Valid Customers updatedCustomer) {
		Customers customer = customersService.getCustomerById(customer_id);
		
		if (customer == null) {
			return new ResponseEntity<Customers>(HttpStatus.NOT_FOUND);
		}
		
		customer.setCustomer_name(updatedCustomer.getCustomer_name());
		customer.setCustomer_email(updatedCustomer.getCustomer_email());
		customer.setCustomer_phone(updatedCustomer.getCustomer_phone());
		Customers savedCustomer = customersService.updateCustomer(customer);
		
		return new ResponseEntity<Customers>(savedCustomer, HttpStatus.CREATED);
	}

	@DeleteMapping("/{customer_id}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable("customer_id") int customer_id) {
		boolean deleted = customersService.deleteCustomerById(customer_id);
		
		if(!deleted) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Customer deleted successfully", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/email/{customer_email}")
	private ResponseEntity<Customers> getUserByEmail(@PathVariable("customer_email") String customer_email) {
		return new ResponseEntity<Customers>(customersService.getCustomerByEmail(customer_email), HttpStatus.OK);
	}
	
	@GetMapping("/{customer_id}/orders")
	public ResponseEntity<List<Orders>> getOrdersByCustomerId(@PathVariable("customer_id") int customerId) {
		return new ResponseEntity<List<Orders>>(customersService.getOrdersByCustomerId(customerId), HttpStatus.OK); 
	}
	
	@GetMapping("/{customer_id}/ratings")
	public ResponseEntity<List<Ratings>> getAllRatingsByCustomerId(@PathVariable("customer_id") int customerId) {
		return new ResponseEntity<List<Ratings>>(customersService.getAllRatingsByCustomerId(customerId), HttpStatus.OK); 
	}
	
	@GetMapping("/{customer_id}/favouriteRestaurant/")
	public ResponseEntity<?> getAllFavoriteRestaurants(@PathVariable("customer_id") int customerId){
		Customers customer = customersService.getCustomerById(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException(" with ID: " + customerId);
		}
		if(customersService.getAllFavoriteRestaurants(customerId).isEmpty()) {
			return new ResponseEntity<String>("no favorite restauranst for "+customerId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Set<Restaurants>>(customersService.getAllFavoriteRestaurants(customerId), HttpStatus.FOUND);
	}
	
	@PostMapping("/{customer_id}/favouriteRestaurant/{restaurant_id}")
	public ResponseEntity<String> addFavoriteRestaurant(@PathVariable("customer_id") int customerId,@PathVariable("restaurant_id") int restaurantId)
			throws InvalidRestaurantIdException{
		Customers customer = customersService.getCustomerById(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException(" with ID: " + customerId);
		}
		
		Restaurants restaurant = restaurantsService.getRestaurantById(restaurantId);
		if (restaurant == null) {
			throw new InvalidRestaurantIdException(" with ID: " + restaurantId);
		}
		customersService.addFavoriteRestaurant(customerId, restaurantId);
		
		return new ResponseEntity<String>("restaurant "+restaurantId+" successfully added favorite restaurant for "+customerId, HttpStatus.OK);
	}
	
	@DeleteMapping("/{customer_id}/favouriteRestaurant/{restaurant_id}")
	public ResponseEntity<String> deleteFavoriteRestaurant(@PathVariable("customer_id") int customerId,@PathVariable("restaurant_id") int restaurantId)
			throws InvalidRestaurantIdException{
		Customers customer = customersService.getCustomerById(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException(" with ID: " + customerId);
		}
		
		Restaurants restaurant = restaurantsService.getRestaurantById(restaurantId);
		if (restaurant == null) {
			throw new InvalidRestaurantIdException(" with ID: " + restaurantId);
		}
		customersService.deleteFavoriteRestaurant(customerId, restaurantId);
		
		return new ResponseEntity<String>("restaurant "+restaurantId+" successfully removed favorite restaurant for "+customerId, HttpStatus.OK);
	}
}
