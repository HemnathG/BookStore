package com.bridgelabz.bs.book.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.bridgelabz.bs.book.dto.CartDto;
import com.bridgelabz.bs.user.model.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Cart {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;	
    private int quantity;
    private double totalPrice;
    
	@OneToOne
    @JoinColumn(name = "userId")
    private User userData;
    
	@ManyToOne
    @JoinColumn(name = "bookId")
	private Book bookData;
	
    public Cart(User userData, Book bookData, CartDto cartDto, double totalPrice){
        this.userData = userData;
        this.updateCart(bookData, cartDto, totalPrice);
    }
	
    public void updateCart(Book bookData, CartDto cartDto, double totalPrice) {
        this.bookData = bookData;
        this.quantity = cartDto.quantity;
        this.totalPrice = totalPrice;
    }
}
