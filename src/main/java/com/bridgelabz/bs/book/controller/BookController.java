package com.bridgelabz.bs.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bs.book.dto.BookDto;
import com.bridgelabz.bs.book.model.Book;
import com.bridgelabz.bs.book.service.IBookService;
import com.bridgelabz.bs.utility.Response;

@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
    private IBookService bookService;

    @GetMapping("/getAllBooks")
    public ResponseEntity<Response> getAllBooks(){
        List<Book> bookList = bookService.getAllBooks();
        Response response = new Response("Get call successful", bookList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/addBook")
    public ResponseEntity<Response> createNewBook(@RequestBody BookDto bookDto){
        Book book = bookService.addNewBook(bookDto);
        Response response = new Response("New Book Data successfully", book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getBookById")
    public ResponseEntity<Response> getBookById(@RequestParam int id){
        Book book = bookService.getBookById(id);
        Response response = new Response("Get call for id successful", book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/editBook")
    public ResponseEntity<Response> editBook(@RequestBody BookDto bookDto, @RequestParam int id){
        Book book = bookService.editBook(id, bookDto);
        Response response = new Response("Book data updated successfully", book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteBook")
    public ResponseEntity<Response> deleteBook(@RequestParam int id){
        bookService.deleteBook(id);
        Response response = new Response("Deleted Successfully", "Book deleted for id: "+id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getBookByName")
    public ResponseEntity<Response> getByBookName(@RequestParam String bookName){
        Book book = bookService.getBookByName(bookName);
        Response response = new Response("Get call for book name successful", book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/updateQty")
    public ResponseEntity<Response> updateQuantity(@RequestParam int id, @RequestBody BookDto bookDto){
        Book book = bookService.updateBookQuantity(id, bookDto);
        Response response = new Response("Book Quantity updated successfully", book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/sortByName")
    public ResponseEntity<Response> sortByBookName(){
        List<Book> bookList = bookService.sortBooks();
        Response response = new Response("Books sorted successfully", bookList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/sortByPriceLowToHigh")
    public ResponseEntity<Response> sortByPriceLowToHigh(){
        List<Book> bookList = bookService.sortBooksByPrice();
        Response response = new Response("Books sorted successfully", bookList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/sortByPriceHighToLow")
    public ResponseEntity<Response> sortByPriceHighToLow(){
        List<Book> bookList = bookService.sortBooksByPriceDesc();
        Response response = new Response("Books sorted successfully", bookList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
