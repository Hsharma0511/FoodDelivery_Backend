package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.DuplicateRestaurantIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Exception.NoSuchRestaurantIDException;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.entity.Restaurants;

import jakarta.transaction.Transactional;
@Service
public class RestaurantsServiceImpl implements RestaurantsService {
	
	@Autowired
    private RestaurantsRepository restaurantsRepository;
	
	@Override
	public List<Restaurants> getAllRestaurants() {
		return restaurantsRepository.findAll();
	}

	@Override
	@Transactional
	public Restaurants updateRestaurant(Restaurants restaurant) throws InvalidRestaurantIdException {
		Optional<Restaurants> previousRest = restaurantsRepository.findById(restaurant.getRestaurant_id());
		
		if(!(previousRest.isPresent())) {
			throw new InvalidRestaurantIdException("Invalid restaurant ID: "+restaurant.getRestaurant_id());
		}
		
		Restaurants restaurantsUpdated = previousRest.get();
		restaurantsUpdated.setRestaurant_name(restaurant.getRestaurant_name());
		restaurantsUpdated.setRestaurant_address(restaurant.getRestaurant_address());
		restaurantsUpdated.setRestaurant_phone(restaurant.getRestaurant_phone());
		Restaurants savedRestaurant = restaurantsRepository.save(restaurantsUpdated);
		
		return savedRestaurant ;
	}
	
	@Override
	public Restaurants getRestaurantById(int restaurantId) throws NoSuchRestaurantIDException {
		return restaurantsRepository.findById(restaurantId).orElseThrow(()-> 
		new NoSuchRestaurantIDException("Restaurant ID: "+ restaurantId +" not found"));
	}

 
	@Override
	public void deleteRestaurantById(int restaurantId) throws InvalidRestaurantIdException {
		if(!restaurantsRepository.existsById(restaurantId)) {
			throw new InvalidRestaurantIdException("Invalid Restaurant ID: "+ restaurantId);
		}
		restaurantsRepository.deleteById(restaurantId);
	}
 
 
	@Override
	public Restaurants addRestaurant(Restaurants restaurant) {
		if(restaurantsRepository.existsById(restaurant.getRestaurant_id())) {
			throw new DuplicateRestaurantIDException("Restaurant ID "+ restaurant.getRestaurant_id() +" already exists");
		}
		return restaurantsRepository.save(restaurant);
	}
}