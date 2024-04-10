package com.fooddelivery.Restaurantsserivce;

import java.util.List;

import com.fooddelivery.model.Ratings;
import com.fooddelivery.model.Restaurants;

public interface RestaurantsService {
	 List<Restaurants> getAllRestaurants();
	 Restaurants updateRestaurants(Restaurants restaurants);
	 //List<Ratings> viewAllRatingsByRestaurant(Restaurants restaurants);
	 
}
