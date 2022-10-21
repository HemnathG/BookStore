package com.bridgelabz.bs.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.bs.book.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	@Query(value = "SELECT * FROM cart WHERE user_id=:id", nativeQuery = true)
    List<Cart> getCartsByUserId(int id);

    @Query(value = "SELECT cart_id FROM cart WHERE book_id=:bookId AND user_id=:userId", nativeQuery = true)
    Integer getExistingItemOfCart(int bookId, int userId);
}
