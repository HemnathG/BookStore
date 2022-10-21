package com.bridgelabz.bs.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bs.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	Book findByBookName(String bookName);
    List<Book> findByOrderByBookName();
    List<Book> findByOrderByPrice();
    List<Book> findByOrderByPriceDesc();
}
