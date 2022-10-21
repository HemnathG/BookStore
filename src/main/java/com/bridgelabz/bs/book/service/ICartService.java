package com.bridgelabz.bs.book.service;

import java.util.List;

import com.bridgelabz.bs.book.dto.CartDto;
import com.bridgelabz.bs.book.model.Cart;

public interface ICartService {

	List<Cart> getAllItems();

	Cart addItem(String token, CartDto cartDto);

	Cart getCartItemById(int id);

	Cart editCart(int id, CartDto cartDto);

	void deleteItem(int id);

	Cart updateBookQuantityInCart(int id, CartDto cartDto);

	List<Cart> getCartItemByUserId(String token);

}
