package com.fooddelivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.fooddelivery.Exception.CustomException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.model.Restaurants;
import com.fooddelivery.service.RestaurantsServiceImpl;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RestaurantsServiceImplTest {
	@Mock
	private RestaurantsRepository restaurantsRepository;

	@InjectMocks
	private RestaurantsServiceImpl restaurantsService;
	
	
	
	@Test
    public void testShowRestaurants() {
        // Arrange
        List<Restaurants> restaurants = new ArrayList<>();
        restaurants.add(new Restaurants(53, "Bella Italia", "123 Main Street", "91234567"));
        restaurants.add(new Restaurants(54, "Tokyo Sushi Bar", "456 Elm Street", "91234897"));
        restaurants.add(new Restaurants(55, "Le Bistro Français", "789 Oak Avenue", "91234667"));
        when(restaurantsRepository.findAll()).thenReturn(restaurants);
        // Act
        List<Restaurants> result = restaurantsService.getAllRestaurants();
        // Assert
        assertEquals(3, result.size());
        assertEquals("Bella Italia", result.get(0).getRestaurant_name());
        assertEquals("Tokyo Sushi Bar", result.get(1).getRestaurant_name());
        assertEquals("Le Bistro Français", result.get(2).getRestaurant_name());
        verify(restaurantsRepository).findAll();
    }
	
	 @Test
	    @Transactional
	    public void testUpdateRestaurants_ValidId() throws InvalidRestaurantIdException {
	        // Mocking the previous state of the restaurant
	        Restaurants existingRestaurant = new Restaurants();
	        existingRestaurant.setRestaurant_id(50); // Existing restaurant ID
	        existingRestaurant.setRestaurant_name("Old Name");
	        existingRestaurant.setRestaurant_address("Old Address");
	        existingRestaurant.setRestaurant_phone("Old Phone");

	        when(restaurantsRepository.findById(existingRestaurant.getRestaurant_id()))
	            .thenReturn(Optional.of(existingRestaurant));

	        // Creating an updated restaurant
	        Restaurants updatedRestaurant = new Restaurants();
	        updatedRestaurant.setRestaurant_id(50); // Existing restaurant ID
	        updatedRestaurant.setRestaurant_name("New Name");
	        updatedRestaurant.setRestaurant_address("New Address");
	        updatedRestaurant.setRestaurant_phone("New Phone");

	        // Calling the method under test
	        Restaurants result = restaurantsService.updateRestaurants(updatedRestaurant);

	        // Verifying that the repository's save method was called with the updated restaurant
	        verify(restaurantsRepository).save(updatedRestaurant);

	        // Asserting that the returned restaurant is the same as the updated one
	        assertEquals(updatedRestaurant, result);
	    }
	}
   
