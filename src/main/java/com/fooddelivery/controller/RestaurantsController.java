package com.fooddelivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fooddelivery.Exception.DuplicateRestaurantIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Exception.NoSuchRestaurantIDException;
import com.fooddelivery.entity.Restaurants;
import com.fooddelivery.service.RestaurantsService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantsController {
	@Autowired
	private RestaurantsService restaurantsService;
	
	@GetMapping("/")
	public ResponseEntity<List<Restaurants>> getAllRestaurants() {
		List<Restaurants> restaurants = restaurantsService.getAllRestaurants();
		return new ResponseEntity<List<Restaurants>>(restaurants, HttpStatus.OK);
	}
	
	@PutMapping("/{restaurant_id}")
	ResponseEntity<Restaurants> updateRestaurants(@RequestBody Restaurants rest) throws InvalidRestaurantIdException {
		Restaurants restaurants = restaurantsService.updateRestaurant(rest);
		
		if(rest.getRestaurant_id() <= 0) {
			throw new InvalidRestaurantIdException("Invalid restaurant ID: "+rest.getRestaurant_id());
		}
		return new ResponseEntity<Restaurants>(restaurants, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{restaurantId}")
	public ResponseEntity<String> getRestaurantsById(@PathVariable("restaurantId") int restaurantId) {
		try {
			Restaurants restaurant = restaurantsService.getRestaurantById(restaurantId);
			return new ResponseEntity<String>(restaurant.toString(), HttpStatus.OK);
		} catch(NoSuchRestaurantIDException e) {
			return new ResponseEntity<String>("Restaurant with ID "+ restaurantId +" not found", HttpStatus.NOT_FOUND);
		}
	}
 
	@PostMapping("/")
	public ResponseEntity<String> addRestaurant(@RequestBody Restaurants restaurant) throws DuplicateRestaurantIDException {
			try {
				Restaurants addedRestaurant = restaurantsService.addRestaurant(restaurant);
				return new ResponseEntity<String>(addedRestaurant.toString(), HttpStatus.OK);
			} catch(DuplicateRestaurantIDException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
	}
	
	@DeleteMapping("/{restaurantId}")
	public ResponseEntity<Void> deleteRestaurantsById(@PathVariable("restaurantId") int restaurantId) throws InvalidRestaurantIdException {
		restaurantsService.deleteRestaurantById(restaurantId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
