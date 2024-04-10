package com.fooddelivery.Restaurantsserivce;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.fooddelivery.Ratingsdao.RatingsRepository;
import com.fooddelivery.Restaurantsdao.RestaurantsRepository;
//import com.fooddelivery.model.Ratings;
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
	public Restaurants updateRestaurants(Restaurants restaurants) {
		Optional<Restaurants> previouRest= restaurantsRepository.findById(restaurants.getRestaurant_id());//Enity is in persistence state
		Restaurants restaurantsupdated=previouRest.get();
		//Do not change the customer id using setter method
		restaurantsupdated.setRestaurant_name(restaurants.getRestaurant_name()); //automatically update data in table
		restaurantsupdated.setRestaurant_address(restaurants.getRestaurant_address());
		restaurantsupdated.setRestaurant_phone(restaurants.getRestaurant_phone());
		return restaurantsupdated ;
		
	}



	
	
	
	

}
