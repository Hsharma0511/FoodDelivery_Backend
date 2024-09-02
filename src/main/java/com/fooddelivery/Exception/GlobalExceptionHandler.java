package com.fooddelivery.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fooddelivery.dto.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler  {
	
	@ExceptionHandler(InvalidRestaurantIdException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCustomerIDException(InvalidRestaurantIdException e){
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
        return responseEntyity;
    }
	
	@ExceptionHandler(InvalidItemIdException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidItemIdException(InvalidItemIdException e){
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
        return responseEntyity;
    }
	
	@ExceptionHandler(InvalidMenuItemException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidMenuItemException(InvalidMenuItemException e){
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
        return responseEntyity;
    }
		
	@ExceptionHandler(CustomerNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex){
		return new ResponseEntity<String>("Customer Not Found "+ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
    @ExceptionHandler(DuplicateCustomerIDException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleDuplicateCustomerIDException(DuplicateCustomerIDException ex){
    	return new ResponseEntity<String>("Conflict: "+ex.getMessage(), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(DuplicateRestaurantIDException.class)
    public ResponseEntity<ExceptionResponse> handleRestaurantNotFoundException(DuplicateRestaurantIDException e){
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
        return responseEntyity;
    }
 	
 	@ExceptionHandler(NoSuchRestaurantIDException.class)
 	public ResponseEntity<String> NoSuchRestaurantIDException(NoSuchRestaurantIDException e){
 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant ID not found");
 	}
 	
 	@ExceptionHandler(OrdersNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleOrdersNotFoundException(OrdersNotFoundException e){
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }
 	
	@ExceptionHandler(InvalidOrderIdException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidOrderIDException(InvalidOrderIdException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response,HttpStatus. CONFLICT);
    }
	
	@ExceptionHandler(DuplicateOrderIdException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateOrderIDException(DuplicateOrderIdException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("Duplicate id");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(DuplicateDriverIDException.class)
    public ResponseEntity<ExceptionResponse> handleRestaurantNotFoundException(DuplicateDriverIDException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler(InvalidDriverIDException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRestaurantIDException(InvalidDriverIDException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(NoSuchDriverIDException.class)
	public ResponseEntity<String> NoSuchRestaurantIDException(NoSuchDriverIDException e){
		return new ResponseEntity<String>("Restaurant ID not found", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		Map<String,String> errors = new HashMap<>();
		
		e.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
