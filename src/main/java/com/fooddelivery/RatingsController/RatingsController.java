package com.fooddelivery.RatingsController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.Ratingsservice.RatingsService;
import com.fooddelivery.model.Ratings;

@RestController
@RequestMapping("/api/restaurants")
public class RatingsController {
	
	@Autowired
    private RatingsService ratingsService;
    
    @GetMapping("/{restaurant_id}/ratings")
    public ResponseEntity<List<Ratings>> getAllRatingsByRestaurantId(@PathVariable int restaurant_id){
    	System.out.println("Ratings for the restaurant retrieved successfully.");
    	List<Ratings> ratings =ratingsService.getAllRatingsByRestaurantId(restaurant_id);
    	if(ratings.isEmpty()) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>(ratings,HttpStatus.OK);
    }

}
