package com.fooddelivery.Restaurantscontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fooddelivery.Restaurantsserivce.RestaurantsService;
import com.fooddelivery.model.Restaurants;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantsController {
	@Autowired
	private RestaurantsService restaurantsService;
	
	@GetMapping(consumes = "application/json", produces="application/json")
	public ResponseEntity<List<Restaurants>> getAllRestaurants(){
		System.out.println("Restaurants fetched Successfully");
		List<Restaurants> restaurants = restaurantsService.getAllRestaurants();
		return new ResponseEntity<>(restaurants,HttpStatus.OK);
	}
	
	@PutMapping("/{restaurant_id}")
	ResponseEntity<Restaurants> updateRestaurants(@RequestBody Restaurants rest){
		Restaurants restaurants=restaurantsService.updateRestaurants(rest);
		System.out.println("Restuarant details updated successfully");		
		return new ResponseEntity<Restaurants>( restaurants,HttpStatus.ACCEPTED);
	}
	
	
	
	

}
