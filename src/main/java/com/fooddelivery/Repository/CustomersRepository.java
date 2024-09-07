package com.fooddelivery.Repository;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.entity.Customers;
import com.fooddelivery.entity.Restaurants;
 
@Repository
public interface CustomersRepository extends JpaRepository<Customers,Integer>{
	
	@Query("select c.restaurantsFavorite from Customers c where c.customer_id=:customerId")
	Set<Restaurants> getAllFavotiteRestaurants(@Param("customerId") int customerId);
	
	@Query("select c from Customers c where c.customer_email=:customer_email")
	Customers getCustomerByEmail(@Param("customer_email") String customer_email);
}
