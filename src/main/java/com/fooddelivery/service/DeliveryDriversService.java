package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.model.DeliveryDrivers;

public interface DeliveryDriversService {
	public List<DeliveryDrivers> getAllDeliveryDrivers();
	public DeliveryDrivers getDeliveryDriversById(int driverId);
	String updateDeliveryDrivers(int driverId, DeliveryDrivers deliverydrivers);
}
