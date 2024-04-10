package com.fooddelivery.Menuitemsservice;


import java.util.List;

import com.fooddelivery.model.MenuItems;

public interface MenuItemsService {
     List<MenuItems> getMenuItemsByRestaurantId(int restaurant_id);
     void addMenuItem(int restaurant_id,MenuItems menuItems);
    // void updateMenuItem(int restaurant_id,int item_id,MenuItems menuItems);
}
