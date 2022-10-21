package com.bridgelabz.bs.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bs.book.dto.CartDto;
import com.bridgelabz.bs.book.model.Book;
import com.bridgelabz.bs.book.model.Cart;
import com.bridgelabz.bs.book.repository.CartRepository;
import com.bridgelabz.bs.exception.CustomException;
import com.bridgelabz.bs.user.model.User;
import com.bridgelabz.bs.user.service.UserService;
import com.bridgelabz.bs.utility.TokenUtility;

@Service
public class CartService implements ICartService{
	@Autowired
    public CartRepository cartRepository;
	
    @Autowired
    public UserService userService;
    
    @Autowired
    public BookService bookService;
    
    @Autowired
    TokenUtility tokenUtility;
    
    public double calculateTotalPrice(int qty, double price){
        return qty*price;
    }
    
    @Override
    public List<Cart> getAllItems() {
        List<Cart> cartList =  cartRepository.findAll();
        if (cartList.isEmpty())
            throw new CustomException("Cart List is empty!");
        return cartList;
    }
    
    @Override
    public Cart addItem(String token, CartDto cartDto) {
        int userId = tokenUtility.decodeToken(token);
        Book book = bookService.getBookById(cartDto.getBookId());
        User user = userService.getUserById(token);
        Integer existingDataId = cartRepository.getExistingItemOfCart(cartDto.getBookId(), userId);
        if(existingDataId == null) {
            if (cartDto.quantity <= book.getQuantity()) {
                double total_price = calculateTotalPrice(cartDto.quantity, book.getPrice());
                Cart cart = new Cart(user, book, cartDto, total_price);
                return cartRepository.save(cart);
            } else throw new CustomException("Book quantity is not enough!");
        }
        else {
            Cart updatedCart = this.updateBookQuantityInCart(existingDataId, cartDto);
            return updatedCart;
        }
    }
    
    @Override
    public Cart getCartItemById(int id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CustomException("Cart with id "+id+" not found!"));
    }
    
    @Override
    public Cart editCart(int id, CartDto cartDto) {
        Cart cart = this.getCartItemById(id);
        Book book = bookService.getBookById(cartDto.getBookId());
        if (cartDto.quantity <= book.getQuantity()) {
            double total_price = calculateTotalPrice(cartDto.quantity, book.getPrice());
            cart.updateCart(book, cartDto, total_price);
            return cartRepository.save(cart);
        }
        else throw new CustomException("Book quantity is not enough!");
    }
    
    @Override
    public void deleteItem(int id) {
        Cart cart = this.getCartItemById(id);
        cartRepository.delete(cart);
    }
    
    @Override
    public Cart updateBookQuantityInCart(int id, CartDto cartDto) {
        Cart cart = this.getCartItemById(id);
        Book book = cart.getBookData();
        if (cartDto.quantity <= book.getQuantity()) {
            cart.setQuantity(cartDto.quantity);
            cart.setTotalPrice(calculateTotalPrice(cartDto.quantity, book.getPrice()));
            return cartRepository.save(cart);
        }
        else throw new CustomException("Book quantity is not enough!");
    }
    
    @Override
    public List<Cart> getCartItemByUserId(String token) {
        int id = tokenUtility.decodeToken(token);
        List<Cart> cartList = cartRepository.getCartsByUserId(id);
        if (cartList.isEmpty())
            throw new CustomException("Cart with User token "+token+" not found!");
        return cartList;
    }
}
