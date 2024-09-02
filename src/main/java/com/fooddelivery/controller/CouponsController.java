package com.fooddelivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.Exception.CouponNotFoundException;
import com.fooddelivery.entity.Coupons;
import com.fooddelivery.service.CouponsService;

@RestController
@RequestMapping("/api/coupons")
public class CouponsController {
	@Autowired
	CouponsService couponsService;
	
	@GetMapping("/")
	public ResponseEntity<List<Coupons>> getAllCoupons() throws CouponNotFoundException {
		List<Coupons> coupons = couponsService.getAllCoupons();
		return new ResponseEntity<List<Coupons>>(coupons, HttpStatus.OK);
	}

}
