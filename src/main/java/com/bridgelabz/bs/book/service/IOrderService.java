package com.bridgelabz.bs.book.service;

import java.util.List;

import com.bridgelabz.bs.book.dto.OrderDto;
import com.bridgelabz.bs.book.model.Order;

public interface IOrderService {

	List<Order> getAllOrders();

	Order placeOrder(String token, OrderDto orderDto);

	Order getOrderItemById(int id);

	List<Order> getOrderItemByUserId(String token);

	void updateOrder(int id);

	List<Order> getPendingOrders();

	void cancelOrder(int id);

}
