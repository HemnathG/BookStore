package com.bridgelabz.bs.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bs.book.dto.BookDto;
import com.bridgelabz.bs.book.model.Book;
import com.bridgelabz.bs.book.repository.BookRepository;
import com.bridgelabz.bs.exception.CustomException;

@Service
public class BookService implements IBookService{
	@Autowired
    private BookRepository repository;
    
	@Override
    public List<Book> getAllBooks() {
        List<Book> bookList = repository.findAll();
        if(bookList.isEmpty())
            throw new CustomException("Book List is empty!");
        return bookList;
    }
    
	@Override
    public Book addNewBook(BookDto bookDto) {
        Book bookExists = repository.findByBookName(bookDto.bookName);
        if(bookExists != null)
            throw new CustomException(bookDto.bookName+" Book already exists.");
        else {
            Book book = new Book(bookDto);
            return repository.save(book);
        }
    }
    
	@Override
    public Book getBookById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException("Book with id "+id+" not found!"));
    }
    
	@Override
    public Book editBook(int id, BookDto bookDto) {
        Book book = this.getBookById(id);
        book.updateBookDetails(bookDto);
        return repository.save(book);
    }
    
	@Override
    public void deleteBook(int id) {
        Book book = this.getBookById(id);
        repository.delete(book);
    }
    
	@Override
    public Book getBookByName(String bookName) {
        Book book = repository.findByBookName(bookName);
        if(book != null)
            return book;
        else
             throw new CustomException("Book with name "+bookName+" not found!");
    }
    
	@Override
    public Book updateBookQuantity(int id, BookDto bookDto) {
        Book book = this.getBookById(id);
        book.setQuantity(bookDto.getQuantity());
        return repository.save(book);
    }
    
	@Override
    public List<Book> sortBooks() {
        List<Book> bookList = repository.findByOrderByBookName();
        if (bookList.isEmpty())
            throw new CustomException("Book List is empty!");
        return bookList;
    }
    
	@Override
    public List<Book> sortBooksByPrice() {
        List<Book> bookList = repository.findByOrderByPrice();
        if (bookList.isEmpty())
            throw new CustomException("Book List is empty!");
        return bookList;
    }
    
	@Override
    public List<Book> sortBooksByPriceDesc() {
        List<Book> bookList = repository.findByOrderByPriceDesc();
        if (bookList.isEmpty())
            throw new CustomException("Book List is empty!");
        return bookList;
    }
}
