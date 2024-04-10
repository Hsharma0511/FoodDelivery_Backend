package com.fooddelivery.Ratingsservice;

import java.util.List;



import com.fooddelivery.model.Ratings;


public interface RatingsService {
	List<Ratings> getAllRatingsByRestaurantId(int restaurant_id);
}
