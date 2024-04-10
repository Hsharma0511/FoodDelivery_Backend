package com.fooddelivery.Ratingsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Ratingsdao.RatingsRepository;
import com.fooddelivery.model.Ratings;
@Service
public class RatingsServiceImpl implements RatingsService {
    
	@Autowired
    private RatingsRepository ratingsRepository;
	
	@Override
	public List<Ratings> getAllRatingsByRestaurantId(int restaurant_id) {
		return ratingsRepository.findByRestaurantId(restaurant_id) ;

	} 

}
