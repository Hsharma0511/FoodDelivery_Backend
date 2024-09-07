package com.fooddelivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.entity.Customers;

@Repository
public interface AuthRepository extends JpaRepository<Customers, Integer> {
	
	@Query("select c from Customers c where c.customer_email=:customer_email AND c.customer_password=:customer_password")
	Customers getCustomerByEmailAndPassword(@Param("customer_email") String customer_email, @Param("customer_password") String customer_password);
}
