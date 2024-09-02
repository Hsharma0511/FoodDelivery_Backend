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

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
import com.fooddelivery.entity.Customers;
import com.fooddelivery.entity.DeliveryDrivers;
import com.fooddelivery.entity.Orders;
import com.fooddelivery.entity.Restaurants;
import com.fooddelivery.service.CustomersService;
import com.fooddelivery.service.DeliveryDriversService;
import com.fooddelivery.service.OrdersService;
import com.fooddelivery.service.RestaurantsService;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
 
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private DeliveryDriversService deliveryDriversService;
	
	@Autowired
	private RestaurantsService restaurantsService;
	
	@Autowired
	private CustomersService customersService;
	
	@GetMapping("/{customer_id}/orders")
	public List<Orders> getOrdersByCustomerId(@PathVariable("customer_id") int customer_id){
		return ordersService.getOrderByCustomerId(customer_id);
	}
	
	@PostMapping("/{orderId}/{customerId}/{restaurantId}/{driverId}")
	public ResponseEntity<Orders> PlaceOrder(@RequestBody Orders orders, @PathVariable("customerId") int customerId, @PathVariable("restaurantId") int restaurantId, @PathVariable("driverId") int driverId)
			throws DuplicateOrderIdException{
		try {
			Customers customer = customersService.getCustomerById(customerId);
			Restaurants restaurants = restaurantsService.getRestaurantById(restaurantId);
			DeliveryDrivers driver = deliveryDriversService.getDeliveryDriversById(driverId);

			orders.setCustomers(customer);
			orders.setRestaurants(restaurants);
			orders.setDeliveryDrivers(driver);

			Orders savedOrder=ordersService.placeOrder(orders);

			System.out.println("Order placed successfully");
			return new ResponseEntity<Orders>(savedOrder,HttpStatus.CREATED);
		}
		catch(Exception e) {
			throw new DuplicateOrderIdException("Order with Id "+orders.getOrder_id()+" already exist");
		}
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<Orders> getOrder(@PathVariable("orderId") int orderId) throws OrdersNotFoundException, InvalidOrderIdException {
		Orders order = ordersService.getOrders(orderId);
		
		if(order == null ) {
			throw new OrdersNotFoundException("Orders not found with Id : "+orderId);
		}
		if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid");
		}
		return new ResponseEntity<Orders>(order, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Orders>> getAllOrders() {
		List<Orders> orders = ordersService.getAllOrders();
		System.out.println("Order details fetched successfully");
		return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/status")
	public ResponseEntity<String> updateOrderStatus(@PathVariable int orderId, @RequestBody String newStatus) throws InvalidOrderIdException, OrdersNotFoundException {
		if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid to update");
		}
		
		Orders order = ordersService.getOrders(orderId);
		
		if(order == null) {
			return ResponseEntity.notFound().build();
		}
		
		order.setOrder_status(newStatus);
		ordersService.updateOrderStatus(orderId, newStatus);
		
		return new ResponseEntity<String>(order.getOrder_status(), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<String> cancelOrder(@PathVariable("orderId") int orderId) throws InvalidOrderIdException{
		if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id with "+orderId+" is not present in the database");
		}
		
		boolean deleted = ordersService.cancelOrder(orderId);
		
		if(deleted) {
			return new ResponseEntity<String>("Order canceled successfully", HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<String>("order not found", HttpStatus.NOT_FOUND);
		}
	}
 
	@PutMapping("/{orderId}/assignDriver/{driverId}")
	public ResponseEntity<String> UpdateDeliveryDriver(@PathVariable("orderId") int orderId, @PathVariable("driverId") int driverId) throws InvalidOrderIdException, OrdersNotFoundException {		
		if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid to update");
		}
		
		Orders orders = ordersService.getOrders(orderId);
		
		if(orders == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		DeliveryDrivers driver = deliveryDriversService.getDeliveryDriversById(driverId);
		orders.setDeliveryDrivers(driver);
		ordersService.UpdateDeliveryDriver(orderId, driverId);
		return new ResponseEntity<String>(orders.getOrder_status(), HttpStatus.ACCEPTED);
	}
}


