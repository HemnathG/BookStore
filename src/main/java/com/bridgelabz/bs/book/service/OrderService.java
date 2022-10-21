package com.bridgelabz.bs.book.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bs.book.dto.OrderDto;
import com.bridgelabz.bs.book.model.Book;
import com.bridgelabz.bs.book.model.Cart;
import com.bridgelabz.bs.book.model.Order;
import com.bridgelabz.bs.book.model.Status;
import com.bridgelabz.bs.book.repository.BookRepository;
import com.bridgelabz.bs.book.repository.CartRepository;
import com.bridgelabz.bs.book.repository.OrderRepository;
import com.bridgelabz.bs.exception.CustomException;
import com.bridgelabz.bs.user.model.Email;
import com.bridgelabz.bs.user.model.User;
import com.bridgelabz.bs.user.repository.UserRepository;
import com.bridgelabz.bs.user.service.EmailService;
import com.bridgelabz.bs.user.service.UserService;
import com.bridgelabz.bs.utility.TokenUtility;

@Service
public class OrderService implements IOrderService {
	@Autowired
    public OrderRepository orderRepository;
	
    @Autowired
    public CartService cartService;
    
    @Autowired
    CartRepository cartRepository;
    
    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    BookService bookService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    TokenUtility tokenUtility;
    
    @Autowired
    EmailService emailService;
    
    @Override
    public List<Order> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        if (orderList.isEmpty())
            throw new CustomException("No orders are placed!");
        return orderList;
    }
    List<Order> orderList = new ArrayList<>();
    
    @Override
    public Order placeOrder(String token, OrderDto orderDto) {
        int userId = tokenUtility.decodeToken(token);
        List<Cart> cart = cartService.getCartItemByUserId(token);
        double totalOrderPrice = 0;
        int totalOrderQty = 0;
        List<Book> orderedBooks = new ArrayList<>();
        User user = userService.getUserById(token);
        String address = "";
        for (int i=0; i<cart.size(); i++){
            totalOrderPrice += cart.get(i).getTotalPrice();
            totalOrderQty += cart.get(i).getQuantity();
            orderedBooks.add(cart.get(i).getBookData());
        }
        if (orderDto.getAddress() == null){
            address = user.getAddress();
        }
        else
            address = orderDto.getAddress();
        Order order = new Order(userId, address, cart, orderedBooks, LocalDate.now(), totalOrderQty, totalOrderPrice, false, Status.PENDING);
        orderList.add(order);
        orderRepository.save(order);
        Email email = new Email(user.getEmail(), "Order placed successfully", "Order Details: "+" => "+order.getBook());
        try {
            emailService.sendMail(email);
        } catch (MessagingException e) {
            throw new CustomException(e.getMessage());
        }
        for (int i=0; i<cart.size(); i++) {
            Book book = cart.get(i).getBookData();
            int updatedQty = this.updateBookQty(book.getQuantity(), cart.get(i).getQuantity());
            book.setQuantity(updatedQty);
            cartRepository.deleteById(cart.get(i).getCartId());
        }
        return order;
    }
    

    private int updateBookQty(int bookQty, int bookQtyInCart) {
        int updatedQty = bookQty - bookQtyInCart;
        if (updatedQty <= 0)
            return 0;
        else
            return updatedQty;
    }
    
    @Override
    public Order getOrderItemById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("No order placed with order id "+id+"!"));
    }
    
    @Override
    public List<Order> getOrderItemByUserId(String token) {
        int id = tokenUtility.decodeToken(token);
        List<Order> orders = orderRepository.findAllByUserId(id);
        if(orders.isEmpty())
                throw new CustomException("No order placed by user with id "+id+"!");
        return orders;
    }

    @Override
    public void updateOrder(int id) {
        orderRepository.updateOrderStatus(id);
    }

    @Override
    public List<Order> getPendingOrders() {
        List<Order> orderList = orderRepository.findPendingOrders();
        if (orderList.isEmpty())
            throw new CustomException("There are no pending orders!");
        return orderList;
    }

    @Override
    public void cancelOrder(int id) {
    	Order order = this.getOrderItemById(id);
        User user = userRepository.getUserById(order.getUserId());
        if (order.isCancel() == false) {
            orderRepository.updateCancel(id);
            Email email = new Email(user.getEmail(), "Order cancelled successfully", "Order Details: "+" => "+order.getBook());
            try {
                emailService.sendMail(email);
            } catch (MessagingException e) {
                throw new CustomException(e.getMessage());
            }
            List<Book> book = order.getBook();
            for (int j = 0; j < orderList.size(); j++) {
                if (orderList.get(j).getOrderId() == id) {
                    for (int i = 0; i < book.size(); i++) {
                        Book updateBook = bookService.getBookById(book.get(i).getBookId());
                        for (int k = 0; k < book.size(); k++) {
                            int orderedBookQty = orderList.get(j).getCart().get(k).getQuantity();
                            int orderedBookId = orderList.get(j).getCart().get(k).getBookData().getBookId();
                            int bookId = updateBook.getBookId();
                            if (orderedBookId == bookId) {
                                int updatedQty = orderedBookQty + updateBook.getQuantity();
                                updateBook.setQuantity(updatedQty);
                                bookRepository.save(book.get(i));
                            }
                        }
                    }
                }
            }
        }
        else throw new CustomException("Order is already canceled!");
    }
}
