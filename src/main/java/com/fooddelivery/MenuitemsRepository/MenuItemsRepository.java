package com.fooddelivery.MenuitemsRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.model.MenuItems;

@Repository
public interface MenuItemsRepository extends JpaRepository<MenuItems,Integer> {
    @Query("SELECT mi FROM MenuItems mi WHERE mi.restaurants.restaurant_id=:restaurant_id")
	List<MenuItems> findByRestaurantId(@Param("restaurant_id")int restaurant_id);
   
}
