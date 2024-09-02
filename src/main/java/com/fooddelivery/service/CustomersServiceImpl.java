package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fooddelivery.Exception.CustomerNotFoundException;
import com.fooddelivery.Exception.DuplicateCustomerIDException;
import com.fooddelivery.Repository.CustomersRepository;
import com.fooddelivery.Repository.OrdersRepository;
import com.fooddelivery.Repository.RatingsRepository;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.entity.Customers;
import com.fooddelivery.entity.Orders;
import com.fooddelivery.entity.Ratings;
import com.fooddelivery.entity.Restaurants;
 
@Service
public class CustomersServiceImpl implements CustomersService {
	@Autowired
	private CustomersRepository customersRepository;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private RatingsRepository ratingsRepository;
	
	@Autowired
	private RestaurantsRepository restaurantsRepository;
 
	@Override
	public List<Customers> getAllCustomers() throws CustomerNotFoundException {
		List<Customers> customers = customersRepository.findAll();
		if(customers.isEmpty()) {
			throw new CustomerNotFoundException("No customers found");
		}
		return customers;
	}
 
	@Override
	public Customers addCustomer(Customers customer) throws DuplicateCustomerIDException {
	    Optional<Customers> existingCustomer = customersRepository.findById(customer.getCustomer_id());
	    if(existingCustomer.isPresent()) {
	    	throw new DuplicateCustomerIDException("Customer with Id " + customer.getCustomer_id() + " already exists");
	    } else {
			return customersRepository.save(customer);
		}
	}
	
	@Override
	public Customers getCustomerById(int customer_id) {
		Optional<Customers> optionalCustomer = customersRepository.findById(customer_id);
		return optionalCustomer.orElse(null) ;
	}
 
	@Override
	public Customers updateCustomer(Customers customer) {
		return customersRepository.save(customer);
	}
 
	@Override
	public boolean deleteCustomerById(int customer_id) {
		Optional<Customers> optionalCustomer = customersRepository.findById(customer_id);
		
		if(optionalCustomer.isPresent()) {
			customersRepository.delete(optionalCustomer.get());
			return true;
		}
		return false;
	}
	
	@Override
	public List<Orders> getOrdersByCustomerId(int customer_id) {
		return ordersRepository.findOrdersByCustomerId(customer_id);
	}

	@Override
	public List<Ratings> getAllRatingsByCustomerId(int customer_id) {
		return ratingsRepository.findByCustomerId(customer_id);
	}
	
	@Override
	public void addFavoriteRestaurant(int customerId, int restaurantId) {
		Optional<Customers> customerOpt = customersRepository.findById(customerId);
		Optional<Restaurants> restaurantOpt = restaurantsRepository.findById(restaurantId);
		
		if(customerOpt.isPresent() && restaurantOpt.isPresent()) {
			Customers customer = customerOpt.get();
			Restaurants restaurant = restaurantOpt.get();
			
			customer.getRestaurantsFavorite().add(restaurant);
			customersRepository.save(customer);
		}
	}

	@Override
	public void deleteFavoriteRestaurant(int customerId, int restaurantId) {
		Optional<Customers> customerOpt = customersRepository.findById(customerId);
		Optional<Restaurants> restaurantOpt = restaurantsRepository.findById(restaurantId);
		
		if(customerOpt.isPresent() && restaurantOpt.isPresent()) {
			Customers customer = customerOpt.get();
			Restaurants restaurant = restaurantOpt.get();
			
			customer.getRestaurantsFavorite().remove(restaurant);
			customersRepository.save(customer);
		}
	}

	@Override
	public Set<Restaurants> getAllFavoriteRestaurants(int customerId) {
		return customersRepository.getAllFavotiteRestaurants(customerId);
	}
}