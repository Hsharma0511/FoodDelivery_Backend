package com.fooddelivery;

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
import com.fooddelivery.Repository.OrdersRepository;
import com.fooddelivery.entity.Orders;
import com.fooddelivery.service.OrdersServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrdersServiceImplTest {

    @InjectMocks
    private OrdersServiceImpl ordersService;

    @Mock
    private OrdersRepository ordersRepository;

    @Test
    public void testGetOrderByCustomerId() {
        Orders order = new Orders(1, new Date(), "Pending");
        List<Orders> orders = List.of(order);
        Mockito.when(ordersRepository.findOrdersByCustomerId(1)).thenReturn(orders);

        List<Orders> result = ordersService.getOrderByCustomerId(1);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Pending", result.get(0).getOrder_status());
    }

    @Test
    public void testPlaceOrder_DuplicateOrderId() {
        Orders order = new Orders(1, new Date(), "Pending");
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.of(order));

        assertThrows(DuplicateOrderIdException.class, () -> {
            ordersService.placeOrder(order);
        });
    }

    @Test
    public void testPlaceOrder_Success() throws DuplicateOrderIdException {
        Orders order = new Orders(1, new Date(), "Pending");
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.empty());
        Mockito.when(ordersRepository.save(order)).thenReturn(order);

        Orders result = ordersService.placeOrder(order);
        assertNotNull(result);
        assertEquals("Pending", result.getOrder_status());
    }

    @Test
    public void testGetOrders_Found() throws OrdersNotFoundException, InvalidOrderIdException {
        Orders order = new Orders(1, new Date(), "Pending");
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.of(order));

        Orders result = ordersService.getOrders(1);
        assertNotNull(result);
        assertEquals("Pending", result.getOrder_status());
    }

    @Test
    public void testGetOrders_NotFound() {
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(OrdersNotFoundException.class, () -> {
            ordersService.getOrders(1);
        });
    }

    @Test
    public void testGetOrders_InvalidId() {
        assertThrows(OrdersNotFoundException.class, () -> {
            ordersService.getOrders(-1);
        });
    }

    @Test
    public void testGetAllOrders() {
        Orders order = new Orders(1, new Date(), "Pending");
        List<Orders> orders = List.of(order);
        Mockito.when(ordersRepository.findAll()).thenReturn(orders);

        List<Orders> result = ordersService.getAllOrders();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Pending", result.get(0).getOrder_status());
    }

    @Test
    public void testUpdateOrderStatus_Success() throws InvalidOrderIdException, OrdersNotFoundException {
        Orders order = new Orders(1, new Date(), "Pending");
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.of(order));
        Mockito.when(ordersRepository.save(order)).thenReturn(order);

        Orders result = ordersService.updateOrderStatus(1, "Delivered");
        assertNotNull(result);
        assertEquals("Delivered", result.getOrder_status());
    }

    @Test
    public void testUpdateOrderStatus_InvalidId() {
        assertThrows(InvalidOrderIdException.class, () -> {
            ordersService.updateOrderStatus(-1, "Delivered");
        });
    }

    @Test
    public void testUpdateOrderStatus_NotFound() {
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(OrdersNotFoundException.class, () -> {
            ordersService.updateOrderStatus(1, "Delivered");
        });
    }

    @Test
    public void testCancelOrder_Success() throws InvalidOrderIdException {
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.of(new Orders()));
        Mockito.when(ordersRepository.existsById(1)).thenReturn(true);

        boolean result = ordersService.cancelOrder(1);
        assertTrue(result);
        Mockito.verify(ordersRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void testCancelOrder_InvalidId() {
        assertThrows(InvalidOrderIdException.class, () -> {
            ordersService.cancelOrder(-1);
        });
    }

    @Test
    public void testCancelOrder_NotFound() {
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidOrderIdException.class, () -> {
            ordersService.cancelOrder(1);
        });
    }
}
