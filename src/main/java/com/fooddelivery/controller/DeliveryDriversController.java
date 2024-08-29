package com.fooddelivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.Exception.DuplicateDriverIDException;
import com.fooddelivery.Exception.InvalidDriverIDException;
import com.fooddelivery.Repository.NoSuchDriverIDException;
import com.fooddelivery.model.DeliveryDrivers;
import com.fooddelivery.service.DeliveryDriversService;

@RestController
@RequestMapping(value="/api/drivers")
public class DeliveryDriversController {
	@Autowired
	private DeliveryDriversService deliverydriversService;
	@GetMapping(consumes = "application/json", produces="application/json")
	public ResponseEntity<?> getAllDrivers() {
        try {
            List<DeliveryDrivers> drivers = deliverydriversService.getAllDeliveryDrivers();
            return ResponseEntity.ok(drivers);
        } catch (NoSuchDriverIDException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
	@GetMapping("/{driverId}")
	 public ResponseEntity<?> getDriverById(@PathVariable int driverId) {
        try {
            DeliveryDrivers driver = deliverydriversService.getDeliveryDriversById(driverId);
            return ResponseEntity.ok(driver);
        } catch (InvalidDriverIDException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/{driverId}")
    public ResponseEntity<String> updateDeliveryDrivers(@PathVariable int driverId, @RequestBody DeliveryDrivers deliverydrivers) {
        try {
            String result = deliverydriversService.updateDeliveryDrivers(driverId, deliverydrivers);
            return ResponseEntity.ok(result);
        } catch (DuplicateDriverIDException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    }
}

