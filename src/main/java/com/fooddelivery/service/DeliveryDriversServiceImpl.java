package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.InvalidDriverIDException;
import com.fooddelivery.Exception.NoSuchDriverIDException;
import com.fooddelivery.Repository.DeliveryDriversRepository;
import com.fooddelivery.entity.DeliveryDrivers;

@Service
public class DeliveryDriversServiceImpl implements DeliveryDriversService {
	@Autowired
	private DeliveryDriversRepository deliverydriversRepository;
	
	@Override
	public List<DeliveryDrivers> getAllDeliveryDrivers() throws NoSuchDriverIDException {
        List<DeliveryDrivers> drivers = deliverydriversRepository.findAll();
        
        if (drivers.isEmpty()) {
            throw new NoSuchDriverIDException("No drivers found.");
        }
        return drivers;
    }
 
	@Override
	public DeliveryDrivers getDeliveryDriversById(int driverId) throws InvalidDriverIDException {
        String driverIdString = String.valueOf(driverId);
        
        try {
            Optional<DeliveryDrivers> driverOptional = deliverydriversRepository.findById(driverId);
            
            if(driverOptional.isPresent()) {
                return driverOptional.get();
            } else {
                throw new InvalidDriverIDException("Driver with ID " + driverId + " not found.");
            }
        } catch(NumberFormatException e) {
        	throw new InvalidDriverIDException("Invalid driver ID format: " + driverIdString);
        }
	}
}
