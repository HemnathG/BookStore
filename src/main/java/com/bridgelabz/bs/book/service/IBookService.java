package com.bridgelabz.bs.book.service;

import java.util.List;

import com.bridgelabz.bs.book.dto.BookDto;
import com.bridgelabz.bs.book.model.Book;

public interface IBookService {

	List<Book> getAllBooks();

	Book addNewBook(BookDto bookDto);

	Book getBookById(int id);

	Book editBook(int id, BookDto bookDto);

	void deleteBook(int id);

	Book getBookByName(String bookName);

	Book updateBookQuantity(int id, BookDto bookDto);

	List<Book> sortBooks();

	List<Book> sortBooksByPrice();

	List<Book> sortBooksByPriceDesc();

}
