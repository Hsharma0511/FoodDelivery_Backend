package com.fooddelivery;

import com.fooddelivery.Exception.DuplicateRestaurantIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Exception.NoSuchRestaurantIDException;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.entity.Restaurants;
import com.fooddelivery.service.RestaurantsServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RestaurantsServiceImplTest {

    @InjectMocks
    private RestaurantsServiceImpl restaurantsService;

    @Mock
    private RestaurantsRepository restaurantsRepository;

    @Test
    public void testGetAllRestaurants() {
        Restaurants restaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        List<Restaurants> restaurants = List.of(restaurant);
        Mockito.when(restaurantsRepository.findAll()).thenReturn(restaurants);

        List<Restaurants> result = restaurantsService.getAllRestaurants();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Tasty Bites", result.get(0).getRestaurant_name());
    }

    @Test
    public void testUpdateRestaurant_Success() throws InvalidRestaurantIdException {
        Restaurants existingRestaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        Restaurants updatedRestaurant = new Restaurants(1, "Tasty Burgers", "123 Main St", "+1234567890");

        Mockito.when(restaurantsRepository.findById(1)).thenReturn(Optional.of(existingRestaurant));
        Mockito.when(restaurantsRepository.save(existingRestaurant)).thenReturn(updatedRestaurant);

        Restaurants result = restaurantsService.updateRestaurant(updatedRestaurant);
        assertNotNull(result);
        assertEquals("Tasty Burgers", result.getRestaurant_name());
    }

    @Test
    public void testUpdateRestaurant_InvalidId() {
        Restaurants restaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        Mockito.when(restaurantsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidRestaurantIdException.class, () -> {
            restaurantsService.updateRestaurant(restaurant);
        });
    }

    @Test
    public void testGetRestaurantById_Found() throws NoSuchRestaurantIDException {
        Restaurants restaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        Mockito.when(restaurantsRepository.findById(1)).thenReturn(Optional.of(restaurant));

        Restaurants result = restaurantsService.getRestaurantById(1);
        assertNotNull(result);
        assertEquals("Tasty Bites", result.getRestaurant_name());
    }

    @Test
    public void testGetRestaurantById_NotFound() {
        Mockito.when(restaurantsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchRestaurantIDException.class, () -> {
            restaurantsService.getRestaurantById(1);
        });
    }

    @Test
    public void testDeleteRestaurantById_Success() throws InvalidRestaurantIdException {
        Mockito.when(restaurantsRepository.existsById(1)).thenReturn(true);

        restaurantsService.deleteRestaurantById(1);
        Mockito.verify(restaurantsRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void testDeleteRestaurantById_InvalidId() {
        Mockito.when(restaurantsRepository.existsById(1)).thenReturn(false);

        assertThrows(InvalidRestaurantIdException.class, () -> {
            restaurantsService.deleteRestaurantById(1);
        });
    }

    @Test
    public void testAddRestaurant_Success() {
        Restaurants restaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        Mockito.when(restaurantsRepository.existsById(1)).thenReturn(false);
        Mockito.when(restaurantsRepository.save(restaurant)).thenReturn(restaurant);

        Restaurants result = restaurantsService.addRestaurant(restaurant);
        assertNotNull(result);
        assertEquals("Tasty Bites", result.getRestaurant_name());
    }

    @Test
    public void testAddRestaurant_DuplicateId() {
        Restaurants restaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        Mockito.when(restaurantsRepository.existsById(1)).thenReturn(true);

        assertThrows(DuplicateRestaurantIDException.class, () -> {
            restaurantsService.addRestaurant(restaurant);
        });
    }
}
