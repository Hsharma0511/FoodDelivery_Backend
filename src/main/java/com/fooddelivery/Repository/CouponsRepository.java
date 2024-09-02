package com.fooddelivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.entity.Coupons;

@Repository
public interface CouponsRepository extends JpaRepository<Coupons, Integer>{

}
