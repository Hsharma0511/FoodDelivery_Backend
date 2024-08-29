package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.DuplicateDriverIDException;
import com.fooddelivery.Exception.InvalidDriverIDException;
import com.fooddelivery.Repository.DeliveryDriversRepository;
import com.fooddelivery.Repository.NoSuchDriverIDException;
import com.fooddelivery.model.DeliveryDrivers;

import jakarta.transaction.Transactional;

@Service
public class DeliveryDriversServiceImpl implements DeliveryDriversService {
	@Autowired
	private DeliveryDriversRepository deliverydriversRepository;
	public DeliveryDriversServiceImpl(DeliveryDriversRepository deliverdriversRepository) {
		this.deliverydriversRepository=deliverydriversRepository;
	}
 
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
            if (driverOptional.isPresent()) {
                return driverOptional.get();
            } else {
                throw new InvalidDriverIDException("Driver with ID " + driverId + " not found.");
            }
            }catch (NumberFormatException e) {
                throw new InvalidDriverIDException("Invalid driver ID format: " + driverIdString);
            }
        }
 
	@Override
	@Transactional
	public String updateDeliveryDrivers(int driverId, DeliveryDrivers deliverydetails) throws DuplicateDriverIDException {
        // Check if the driver with the given ID already exists
        Optional<DeliveryDrivers> opt = deliverydriversRepository.findById(driverId);
        if (opt.isPresent()) {
            throw new DuplicateDriverIDException("Driver with ID " + driverId + " already exists.");
        }
        return "Driver with ID " + driverId + " updated successfully";
}

}
