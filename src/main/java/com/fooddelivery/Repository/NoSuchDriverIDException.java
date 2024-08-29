package com.fooddelivery.Repository;

public class NoSuchDriverIDException extends RuntimeException {
	public NoSuchDriverIDException(String message) {
		super(message);
	}
 
}