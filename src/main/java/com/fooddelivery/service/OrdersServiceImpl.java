package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
import com.fooddelivery.Repository.DeliveryDriversRepository;
import com.fooddelivery.Repository.OrdersRepository;
import com.fooddelivery.entity.DeliveryDrivers;
import com.fooddelivery.entity.Orders;

import jakarta.transaction.Transactional;

 
@Service
public class OrdersServiceImpl implements OrdersService{
 
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private DeliveryDriversRepository deliveryDriversRepository;
	
	@Override
	public List<Orders> getOrderByCustomerId(int customer_id) {
		return ordersRepository.findOrdersByCustomerId(customer_id);
	}
	
	@Override
	public List<Orders> getOrdersByDriverId(int driverId) {
		return ordersRepository.findOrdersByDriverId(driverId);
	}
	
	@Override
	@Transactional
	public Orders placeOrder(Orders orders) throws DuplicateOrderIdException{  
		Optional<Orders> existingOrder = ordersRepository.findById(orders.getOrder_id());
		
		if(existingOrder.isPresent()) {
			throw new DuplicateOrderIdException("Order with Id "+orders.getOrder_id()+" already exist");
		}
		else {
			return ordersRepository.save(orders);
		}
	}
 
	@Override
	@Transactional
	public Orders getOrders(int orderId) throws OrdersNotFoundException ,InvalidOrderIdException{
		Optional<Orders> orders = ordersRepository.findById(orderId);
		
		if(orders.isEmpty()) {
			throw new OrdersNotFoundException("Orders not found with Id: "+orderId);
		}
		if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid ");
		}
		return orders.orElse(null);
	}
 
	@Override
	@Transactional
	public List<Orders> getAllOrders() {
		return ordersRepository.findAll();
	}
 
	@Override
	@Transactional
	public Orders updateOrderStatus(int orderId, String newStatus) throws InvalidOrderIdException, OrdersNotFoundException{
		Optional<Orders> OpOrder = ordersRepository.findById(orderId);
		
		if(OpOrder.isPresent()) {
			Orders order = OpOrder.get();
			order.setOrder_status(newStatus);
			ordersRepository.save(order);
			return ordersRepository.save(order);
		}
		else if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid to update");
		}
		else {
			throw new OrdersNotFoundException("Order not found with id: "+orderId);
		}
 
	}
 
	@Override
	@Transactional
	public boolean cancelOrder(int orderId) throws InvalidOrderIdException {
		Optional<Orders> optionalOrder = ordersRepository.findById(orderId);
		
		if(optionalOrder.isPresent()) {
			ordersRepository.deleteById(orderId);
			return true;
		}
		if(!optionalOrder.isPresent() || orderId <= 0) {
			throw new InvalidOrderIdException("Order Id with "+orderId+" is not present");
		}
		return false;
	}

	@Override
	@Transactional
	public Orders UpdateDeliveryDriver(int orderId, int driverId) throws InvalidOrderIdException, OrdersNotFoundException {
		Optional<Orders> OpOrders = ordersRepository.findById(orderId);
		
		if(OpOrders.isPresent()) {
			Orders order = OpOrders.get();
			DeliveryDrivers driver = deliveryDriversRepository.findById(driverId).get();
			order.setDeliveryDrivers(driver);
			ordersRepository.save(order);
			return ordersRepository.save(order);
		}
		else if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid to update");
		}
		else {
			throw new OrdersNotFoundException("Order not found with id: "+orderId);
		}
	}
}