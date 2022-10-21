package com.bridgelabz.bs.book.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bridgelabz.bs.book.dto.BookDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Book {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    private String bookName;
    private String authorName; 
    private String bookDescription; 
    private String bookImage;
    private double price;
    private int quantity;

    public Book(BookDto bookDto){ 
    	this.updateBookDetails(bookDto); 
    }
    
    public void updateBookDetails(BookDto bookDto){
        this.bookName=bookDto.bookName;
        this.authorName=bookDto.authorName;
        this.bookDescription=bookDto.bookDescription;
        this.bookImage=bookDto.bookImage;
        this.price=bookDto.price;
        this.quantity=bookDto.quantity;
    }
}
