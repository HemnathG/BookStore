package com.bridgelabz.bs.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bs.book.dto.OrderDto;
import com.bridgelabz.bs.book.model.Order;
import com.bridgelabz.bs.book.service.IOrderService;
import com.bridgelabz.bs.utility.Response;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {
	@Autowired
    public IOrderService orderService;
	
    @GetMapping( "/getOrders")
    public ResponseEntity<Response> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        Response response = new Response("Get call for orders successful", orders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/placeOrder")
    public ResponseEntity<Response> placeOrder(@RequestHeader String token, @RequestBody OrderDto orderDto){
        Order order = orderService.placeOrder(token, orderDto);
        Response response = new Response("Order placed successfully", order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getOrderById")
    public ResponseEntity<Response> getOrderById(@RequestParam int id){
        Order order = orderService.getOrderItemById(id);
        Response response = new Response("Order details for order id "+id+" successful", order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getOrderByUserId")
    public ResponseEntity<Response> getOrderById(@RequestParam String token){
        List<Order> order = orderService.getOrderItemByUserId(token);
        Response response = new Response("Order details for user successful", order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("cancelOrder")
    public ResponseEntity<Response> cancelOrder(@RequestParam int id){
        orderService.cancelOrder(id);
        Response response = new Response("Order cancel for id successful", "Order cancelled for id: "+id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("updateOrder")
    public ResponseEntity<Response> updateOrder(@RequestParam int id){
        orderService.updateOrder(id);
        Response response = new Response("Order updated for id successful", "Order completed for id: "+id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getPendingOrders")
    public ResponseEntity<Response> getPendingOrders(){
        List<Order> pendingOrders = orderService.getPendingOrders();
        Response response = new Response("Get call for all pending orders successful", pendingOrders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
