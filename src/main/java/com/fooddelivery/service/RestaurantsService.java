package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.entity.Restaurants;

public interface RestaurantsService {
	 List<Restaurants> getAllRestaurants();
	 Restaurants updateRestaurant(Restaurants restaurant) throws InvalidRestaurantIdException;
	 public Restaurants getRestaurantById(int restaurantId);
	 void deleteRestaurantById(int restaurantId) throws InvalidRestaurantIdException;
     public Restaurants addRestaurant(Restaurants restaurants);
}