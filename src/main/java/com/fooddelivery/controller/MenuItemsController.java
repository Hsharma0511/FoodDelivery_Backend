package com.fooddelivery.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fooddelivery.Exception.InvalidItemIdException;
import com.fooddelivery.Exception.InvalidMenuItemException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.entity.MenuItems;
import com.fooddelivery.service.MenuItemsService;

@RestController
@RequestMapping("/api/restaurants")
public class MenuItemsController {

	@Autowired
	private MenuItemsService menuItemsService;

	@GetMapping("/{restaurant_id}/menu")
	public ResponseEntity<?> getMenuItemsByRestaurantId(@PathVariable("restaurant_id") int restaurant_id) throws InvalidRestaurantIdException {
		try {
			List<MenuItems> menuItem = menuItemsService.getMenuItemsByRestaurantId(restaurant_id);
			return new ResponseEntity<List<MenuItems>>(menuItem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage() ,HttpStatus.NOT_FOUND);
//			throw new InvalidRestaurantIdException("Restaurant with ID: "+restaurant_id+" does not exist!");
		}
	}

	@PostMapping("/{restaurant_id}/menu")
	public ResponseEntity<MenuItems> addmenuItemsByRestaurantId(@PathVariable("restaurant_id") int restaurant_id,
			@RequestBody MenuItems menuItems) throws InvalidRestaurantIdException {
		MenuItems savedItems = menuItemsService.addMenuItems(restaurant_id, menuItems);
		if (savedItems == null) {
			throw new InvalidRestaurantIdException("Restaurant Id is not found with Id: " + restaurant_id);
		}
		return new ResponseEntity<MenuItems>(savedItems, HttpStatus.CREATED);

	}

	@PutMapping("/{restaurant_id}/menu/{item_id}")
	public ResponseEntity<MenuItems> updatemenuItems(@PathVariable("restaurant_id") int restaurant_id, @PathVariable("item_id") int item_id,
			@RequestBody MenuItems menuItems) throws InvalidMenuItemException {

		if (menuItems.getItem_name() == null || menuItems.getItem_name().isEmpty()) {
			throw new InvalidMenuItemException("Item Name cannot be empty");
		}

		if (menuItems.getItem_price() <= 0) {
			throw new InvalidMenuItemException("Price must be greater than 0");
		}

		MenuItems updatedItems = menuItemsService.updateMenuItems(restaurant_id, item_id, menuItems);
		return new ResponseEntity<MenuItems>(updatedItems, HttpStatus.OK);
	}

	@DeleteMapping("/{restaurant_id}/menu/{item_id}")
	public ResponseEntity<MenuItems> deletemenuItems(@PathVariable("restaurant_id") int restaurant_id, @PathVariable("item_id") int item_id)
			throws InvalidItemIdException {

		try {
			menuItemsService.deleteMenuItems(restaurant_id, item_id);
			return new ResponseEntity<MenuItems>(HttpStatus.NO_CONTENT);
		} catch (InvalidItemIdException e) {
			throw new InvalidItemIdException("Item Id is not found with Id: " + item_id);
		}
	}
}
