package com.fooddelivery.MenuItemsController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.Menuitemsservice.MenuItemsService;
import com.fooddelivery.model.MenuItems;

@RestController
@RequestMapping("/api/restaurant")
public class MenuItemsController {
	
    @Autowired
    private MenuItemsService menuItemsService;
    
    @GetMapping("/{restaurant_id}/menu")
    public ResponseEntity<List<MenuItems>> getMenuItemsByRestaurantId(@PathVariable int restaurant_id){
    	List<MenuItems> menuItems = menuItemsService.getMenuItemsByRestaurantId(restaurant_id);
    	if(menuItems.isEmpty()) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>(menuItems,HttpStatus.OK);
    }
    
    @PostMapping("/{restaurant_id}/menu")
    public ResponseEntity<String> addMenuItem(@PathVariable int restaurant_id, @RequestBody MenuItems menuItems)
    {
    	menuItemsService.addMenuItem(restaurant_id, menuItems);
    	return ResponseEntity.status(HttpStatus.CREATED).body("Menu item added to the restaurant successfully");
    }
   
}
