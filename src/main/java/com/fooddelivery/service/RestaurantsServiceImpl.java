package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.DuplicateRestaurantIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Exception.NoSuchRestaurantIDException;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.model.Restaurants;

import jakarta.transaction.Transactional;
@Service
public class RestaurantsServiceImpl implements RestaurantsService {
	
	@Autowired
    private RestaurantsRepository restaurantsRepository;
	
	
	
	public RestaurantsServiceImpl(RestaurantsRepository restaurantsRepository) {
		this.restaurantsRepository=restaurantsRepository;
	}
	
	@Override
	public List<Restaurants> getAllRestaurants() {
		return restaurantsRepository.findAll();
	}

	@Override
	@Transactional
	public Restaurants updateRestaurants(Restaurants restaurants) throws InvalidRestaurantIdException {
		Optional<Restaurants> previousRest= restaurantsRepository.findById(restaurants.getRestaurant_id());//Enity is in persistence state
		if(!(previousRest.isPresent())) {
			throw new InvalidRestaurantIdException("Invalid restaurant ID: "+restaurants.getRestaurant_id());
		}
		Restaurants restaurantsupdated=previousRest.get();
		//Do not change the customer id using setter method
		restaurantsupdated.setRestaurant_name(restaurants.getRestaurant_name()); //automatically update data in table
		restaurantsupdated.setRestaurant_address(restaurants.getRestaurant_address());
		restaurantsupdated.setRestaurant_phone(restaurants.getRestaurant_phone());
		Restaurants savedRestaurant = restaurantsRepository.save(restaurantsupdated);
		return savedRestaurant ;
	}
	
	@Override
	public Restaurants getRestaurantsById(int restaurantId) throws NoSuchRestaurantIDException {
		return restaurantsRepository.findById(restaurantId).orElseThrow(()-> 
		new NoSuchRestaurantIDException(" Restaurant ID :"+ restaurantId +"not found"));
		}

 
	@Override
	public void deleteRestaurantsById(int restaurantId) throws InvalidRestaurantIdException {
		if(!restaurantsRepository.existsById(restaurantId)) {
			throw new InvalidRestaurantIdException("Invalid Restaurant ID: "+ restaurantId);
		}
		restaurantsRepository.deleteById(restaurantId);
	}
 
 
	@Override
	public Restaurants addRestaurants(Restaurants restaurants) {
		if(restaurantsRepository.existsById(restaurants.getRestaurant_id())) {
			throw new DuplicateRestaurantIDException("Restaurant ID"+ restaurants.getRestaurant_id() +"already exists");
		}
		return restaurantsRepository.save(restaurants);
	}

}