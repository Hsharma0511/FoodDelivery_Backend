package com.fooddelivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.CustomerNotFoundException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Repository.RatingsRepository;
import com.fooddelivery.entity.Ratings;
@Service
public class RatingsServiceImpl implements RatingsService {
    
	@Autowired
    private RatingsRepository ratingsRepository;
	
	@Override
	public List<Ratings> getAllRatingsByRestaurantId(int restaurant_id) throws InvalidRestaurantIdException {
		List<Ratings> ratings = ratingsRepository.findByRestaurantId(restaurant_id);
		
		if(ratings.isEmpty()) {
			throw new InvalidRestaurantIdException("No ratings found for restaurant with ID "+restaurant_id);
		}
		return ratings;
	}
	
	@Override
	public List<Ratings> getAllRatingsByCustomerId(int customer_id) throws CustomerNotFoundException {
		List<Ratings> ratings = ratingsRepository.findByCustomerId(customer_id);
		
		if(ratings.isEmpty()) {
			throw new CustomerNotFoundException("No ratings found for customer with ID "+customer_id);
		}
		return ratings;
	}
}