package com.fooddelivery.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="CUSTOMERS")
public class Customers {
    @Id
    @Column(name="CUSTOMER_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int customer_id;
    
    @Column(name="CUSTOMER_NAME")
    @NotEmpty(message="Customer name cannot be empty")
    @Size(max = 50, message = "Customer name must be less than or equal to 50 characters")
    private String customer_name;
    
    @Column(name="CUSTOMER_EMAIL")
    @NotEmpty(message="Customer email cannot be empty")
    @Email(message="Customer email should be valid")
    private String customer_email;
    
    @Column(name="CUSTOMER_PHONE")
    @NotEmpty(message="Customer phone cannot be empty")
    @Pattern(regexp="^[+]?[0-9]{10,13}$", message="Customer phone should be a valid phone number")
    private String customer_phone;
    
    @ManyToMany
    @JoinTable(name = "customer_favorite_restaurant", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
    @JsonIgnore
    private Set<Restaurants> restaurantsFavorite;
    
	public Customers(int customer_id, String customer_name, String customer_email, String customer_phone) {
		super();
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.customer_email = customer_email;
		this.customer_phone = customer_phone;
	}
	
	public Customers() {}
    
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_email() {
		return customer_email;
	}
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	public String getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	public Set<Restaurants> getRestaurantsFavorite() {
		return restaurantsFavorite;
	}

	public void setRestaurantsFavorite(Set<Restaurants> restaurantsFavorite) {
		this.restaurantsFavorite = restaurantsFavorite;
	}

	@Override
	public String toString() {
		return "Customers [customer_id=" + customer_id + ", customer_name=" + customer_name + ", customer_email="
				+ customer_email + ", customer_phone=" + customer_phone + "]";
	}
}
