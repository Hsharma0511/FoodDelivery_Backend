package com.fooddelivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.CouponNotFoundException;
import com.fooddelivery.Repository.CouponsRepository;
import com.fooddelivery.entity.Coupons;

@Service
public class CouponsServiceImpl implements CouponsService{

	@Autowired
	private CouponsRepository couponsRepository;
	
	@Override
	public List<Coupons> getAllCoupons() throws CouponNotFoundException {
		List<Coupons> coupons=couponsRepository.findAll();
		if(coupons.isEmpty()) {
			throw new CouponNotFoundException("No coupons found");
		}
		return coupons;
	}
}
