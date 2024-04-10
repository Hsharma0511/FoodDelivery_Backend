package com.fooddelivery.Menuitemsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.MenuitemsRepository.MenuItemsRepository;
import com.fooddelivery.model.MenuItems;
@Service
public class MenuItemsServiceImpl implements MenuItemsService {
    
	
	@Autowired
	private MenuItemsRepository menuItemsRepository;
	@Override
	public List<MenuItems> getMenuItemsByRestaurantId(int restaurant_id) {
		
		return menuItemsRepository.findByRestaurantId(restaurant_id) ;
	}
	@Override
	public void addMenuItem(int restaurant_id, MenuItems menuItems) {
		menuItems.setRestaurant_id(restaurant_id);
		menuItemsRepository.save(menuItems);
		
	}
	

}
